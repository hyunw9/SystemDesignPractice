package systemdesign.shard_demo.external


class ThreadLocalDatabaseContextHolder(
) {
    companion object {
        private val currentKey: ThreadLocal<Long> = ThreadLocal()

        fun setShardKey(key: Long) = currentKey.set(key)

        fun getShardKey(): Long? = currentKey.get()

        fun clear() = currentKey.remove()

    }
}
