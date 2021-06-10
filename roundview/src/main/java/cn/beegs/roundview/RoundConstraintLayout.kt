package cn.beegs.roundview

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout

/**
 * @see RoundViewHelper
 */
class RoundConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        RoundViewHelper.compileBg(this, context, attrs, defStyleAttr)
    }
}
