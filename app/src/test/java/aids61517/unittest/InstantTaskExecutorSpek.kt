
package aids61517.unittest

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import org.spekframework.spek2.Spek

object InstantTaskExecutorSpek : Spek({
  beforeGroup {
    ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
      override fun executeOnDiskIO(runnable: Runnable) {
        runnable.run()
      }

      override fun postToMainThread(runnable: Runnable) {
        runnable.run()
      }

      override fun isMainThread(): Boolean {
        return true
      }
    })
  }
  afterGroup {
    ArchTaskExecutor.getInstance().setDelegate(null)
  }
})