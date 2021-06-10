package cn.beegs.roundview

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * @see RoundViewHelper
 */
class RoundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    init {
        RoundViewHelper.compileBg(this, context, attrs, defStyleAttr)
    }
}
