import java.util.concurrent.TimeUnit

class ExpirableCache(private val delgate: Cache,
                     private val flushInterval: Long = TimeUnit.MINUTES.toMillis(1)): Cache by delgate {


    private  val lastFlushTime = System.nanoTime()

    override val size: Int
        get() {
            recycle()
            return delgate.size
        }

    override fun remove(key: Any): Any? {
        recycle()
        return delgate.remove(key)
    }

    override fun get(key: Any): Any? {
        recycle()
        return delgate[key]
    }

    private fun recycle() {
        val shouldRecycle = System.nanoTime() - lastFlushTime >= TimeUnit.MILLISECONDS.toNanos(flushInterval)
        if (!shouldRecycle) return
        delgate.clear()
    }

}