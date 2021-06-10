package cn.beegs.roundview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * @see RoundViewHelper
 */
class RoundFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    init {
        RoundViewHelper.compileBg(this, context, attrs, defStyleAttr)
    }
}
