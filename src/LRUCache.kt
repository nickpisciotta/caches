class LRUCache(private val delegate: Cache, private val minimalSize: Int = DEFAULT_SIZE): Cache by delegate {

    private val keyMap = object: LinkedHashMap<Any, Any>(minimalSize, 0.75f, true) {
        override fun removeEldestEntry(eldest: MutableMap.MutableEntry<Any, Any>): Boolean {
            val tooManyCachedItems = size > minimalSize
            if (tooManyCachedItems) eldestKeyToRemove = eldest.key
            return tooManyCachedItems
        }
    }

    private var eldestKeyToRemove: Any? = null

    override fun set(key: Any, value: Any) {
        delegate[key] = value
        cycleKeyMap(key)
    }

    override fun get(key: Any): Any? {
        keyMap[key]
        return delegate[key]
    }

    override fun clear() {
        keyMap.clear()
        delegate.clear()
    }


    private fun cycleKeyMap(key: Any) {
        keyMap[key] = PRESENT
        eldestKeyToRemove?.let { delegate.remove(it) }
        eldestKeyToRemove = null
    }


    companion object {
        private const val DEFAULT_SIZE = 1000
        private const val PRESENT = true
    }
}