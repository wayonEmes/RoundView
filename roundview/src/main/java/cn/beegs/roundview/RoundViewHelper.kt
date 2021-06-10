package cn.beegs.roundview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View


/**
 * @see RoundDrawable
 */
internal object RoundViewHelper {
    /**
     * 转换背景为RoundDrawable 可进行View的bg自定义
     *
     * @param view 需要设置背景的view
     */
    fun compileBg(view: View, context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        if (view.background == null) {
            setBackgroundKeepingPadding(
                view,
                RoundDrawable.fromAttributeSet(context, attrs, defStyleAttr)
            )
        }
    }

    private fun setBackgroundKeepingPadding(view: View, drawable: Drawable) {
        val padding =
            intArrayOf(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom)
        view.background = drawable
        view.setPadding(padding[0], padding[1], padding[2], padding[3])
    }

}
