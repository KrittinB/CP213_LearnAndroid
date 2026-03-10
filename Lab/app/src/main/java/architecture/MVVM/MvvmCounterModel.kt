package architecture.MVVM

class MvvmCounterModel {
    private var count = 0

    fun getCount(): Int {
        return count
    }

    fun incrementCounter() {
        count++
    }

    fun DecrementCounter() {
        count--
    }
}
