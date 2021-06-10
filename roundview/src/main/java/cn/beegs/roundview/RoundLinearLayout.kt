package cn.beegs.roundview

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * @see RoundViewHelper
 */
class RoundLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        RoundViewHelper.compileBg(this, context, attrs, defStyleAttr)
    }
}
