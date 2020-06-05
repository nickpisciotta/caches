class PermanentCache : Cache {
    private val cache = HashMap<Any, Any>()

    override val size: Int
        get() = cache.size

    override fun set(key: Any, value: Any) {
        this.cache[key] = value;
    }

    override fun get(key: Any): Any? = this.cache[key]

    override fun remove(key: Any) = this.cache.remove(key);

    override fun clear() = this.cache.clear()
}