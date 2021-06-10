package cn.beegs.roundview

import android.content.Context
import android.util.AttributeSet

/**
 * @see RoundViewHelper
 */
class RoundButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : androidx.appcompat.widget.AppCompatButton(context, attrs, defStyleAttr) {
    init {
        RoundViewHelper.compileBg(this, context, attrs, defStyleAttr)
    }
}
