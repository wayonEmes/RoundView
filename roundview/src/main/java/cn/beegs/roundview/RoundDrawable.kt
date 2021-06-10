package cn.beegs.roundview

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import org.xmlpull.v1.XmlPullParserException
import kotlin.math.min

/**
 * 使View能方便地指定圆角、边框颜色、边框粗细、背景色
 *
 * 注意: 因为该控件的圆角采用 View 的 background 实现, 所以与原生的 `android:background` 有冲突。
 *
 *  * 如果在 xml 中用 `android:background` 指定 background, 该 background 不会生效。
 *  * 如果在该 View 构造完后用 [View.setBackgroundResource] 等方法设置背景, 该背景将覆盖圆角效果。
 *
 * 如需在 xml 中指定圆角、边框颜色、边框粗细、背景色等值,采用 xml 属性 [cn.beegs.roundview.R.styleable.RoundView]
 *
 * 如需在 Java 中指定以上属性, 需要通过 [View.getBackground] 获取 [RoundDrawable] 对象,
 * 然后使用 [RoundDrawable] 提供的方法进行设置。
 */
class RoundDrawable : GradientDrawable() {

    /**
     * 圆角大小是否自适应为 View 的高度的一般
     */
    private var mRadiusAdjustBounds = true
    private var mFillColors: ColorStateList? = null
    private var mStrokeWidth = 0
    private var mStrokeColors: ColorStateList? = null

    /**
     * 设置按钮的背景色(只支持纯色,不支持 Bitmap 或 Drawable)
     */
    fun setBgData(colors: ColorStateList?) {
        if (hasNativeStateListAPI()) {
            super.setColor(colors)
        } else {
            mFillColors = colors
            val currentColor = colors?.getColorForState(state, Color.TRANSPARENT)
                ?: Color.TRANSPARENT
            setColor(currentColor)
        }
    }

    /**
     * 设置按钮的描边粗细和颜色
     */
    fun setStrokeData(width: Int, colors: ColorStateList?) {
        if (hasNativeStateListAPI()) {
            super.setStroke(width, colors)
        } else {
            mStrokeWidth = width
            mStrokeColors = colors
            val currentColor: Int = colors?.getColorForState(state, Color.TRANSPARENT)
                ?: Color.TRANSPARENT
            setStroke(width, currentColor)
        }
    }

    private fun hasNativeStateListAPI(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * 设置圆角大小是否自动适应为 View 的高度的一半
     */
    fun setIsRadiusAdjustBounds(isRadiusAdjustBounds: Boolean) {
        mRadiusAdjustBounds = isRadiusAdjustBounds
    }

    override fun onStateChange(stateSet: IntArray): Boolean {
        var superRet = super.onStateChange(stateSet)
        if (mFillColors != null) {
            val color = mFillColors!!.getColorForState(stateSet, Color.TRANSPARENT)
            setColor(color)
            superRet = true
        }
        if (mStrokeColors != null) {
            val color = mStrokeColors!!.getColorForState(stateSet, Color.TRANSPARENT)
            setStroke(mStrokeWidth, color)
            superRet = true
        }
        return superRet
    }

    override fun isStateful(): Boolean {
        return (mFillColors?.isStateful == true || mStrokeColors?.isStateful == true || super.isStateful())
    }

    override fun onBoundsChange(r: Rect) {
        super.onBoundsChange(r)
        if (mRadiusAdjustBounds) {
            // 修改圆角为短边的一半
            cornerRadius = (min(r.width(), r.height()) / 2).toFloat()
        }
    }

    fun setGradientAngle(gradientAngle: Int, typedArray: TypedArray? = null) {
        var angle = gradientAngle
        angle %= 360

        if (angle % 45 != 0 && typedArray != null) {
            throw XmlPullParserException(
                typedArray.positionDescription
                        + "<gradient> tag requires 'angle' attribute to "
                        + "be a multiple of 45"
            )
        }

        orientation = when (angle) {
            0 -> Orientation.LEFT_RIGHT
            45 -> Orientation.BL_TR
            90 -> Orientation.BOTTOM_TOP
            135 -> Orientation.BR_TL
            180 -> Orientation.RIGHT_LEFT
            225 -> Orientation.TR_BL
            270 -> Orientation.TOP_BOTTOM
            315 -> Orientation.TL_BR
            else -> Orientation.LEFT_RIGHT
        }
    }

    companion object {

        fun fromAttributeSet(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int
        ): RoundDrawable {
            val bg = RoundDrawable()
            if (attrs != null) {
                val typedArray =
                    context.obtainStyledAttributes(attrs, R.styleable.RoundView, defStyleAttr, 0)
                val colorBg = typedArray.getColorStateList(R.styleable.RoundView_rv_backgroundColor)
                val colorBorder = typedArray.getColorStateList(R.styleable.RoundView_rv_borderColor)
                val borderWidth =
                    typedArray.getDimensionPixelSize(R.styleable.RoundView_rv_borderWidth, 0)
                var isRadiusAdjustBounds =
                    typedArray.getBoolean(R.styleable.RoundView_rv_isRadiusAdjustBounds, false)
                val mRadius = typedArray.getDimensionPixelSize(R.styleable.RoundView_rv_radius, 0)
                val mRadiusTopLeft =
                    typedArray.getDimensionPixelSize(R.styleable.RoundView_rv_radiusTopLeft, 0)
                val mRadiusTopRight =
                    typedArray.getDimensionPixelSize(R.styleable.RoundView_rv_radiusTopRight, 0)
                val mRadiusBottomLeft =
                    typedArray.getDimensionPixelSize(R.styleable.RoundView_rv_radiusBottomLeft, 0)
                val mRadiusBottomRight =
                    typedArray.getDimensionPixelSize(R.styleable.RoundView_rv_radiusBottomRight, 0)
                val mGradientStartColor =
                    typedArray.getColor(R.styleable.RoundView_rv_gradientStartColor, 0)
                val mGradientEndColor =
                    typedArray.getColor(R.styleable.RoundView_rv_gradientEndColor, 0)
                val mGradientAngle = typedArray.getInt(R.styleable.RoundView_rv_gradientAngle, 0)
                typedArray.recycle()

                bg.setBgData(colorBg)
                bg.setStrokeData(borderWidth, colorBorder)
                if (mRadiusTopLeft > 0 || mRadiusTopRight > 0 || mRadiusBottomLeft > 0 || mRadiusBottomRight > 0) {
                    val radii = floatArrayOf(
                        mRadiusTopLeft.toFloat(),
                        mRadiusTopLeft.toFloat(),
                        mRadiusTopRight.toFloat(),
                        mRadiusTopRight.toFloat(),
                        mRadiusBottomRight.toFloat(),
                        mRadiusBottomRight.toFloat(),
                        mRadiusBottomLeft.toFloat(),
                        mRadiusBottomLeft.toFloat()
                    )
                    bg.cornerRadii = radii
                    isRadiusAdjustBounds = false
                } else {
                    bg.cornerRadius = mRadius.toFloat()
                    if (mRadius > 0) {
                        isRadiusAdjustBounds = false
                    }
                }
                if (mGradientStartColor != 0 && mGradientEndColor != 0) {
                    bg.colors = intArrayOf(mGradientStartColor, mGradientEndColor)
                }
                bg.setGradientAngle(mGradientAngle, typedArray)
                bg.setIsRadiusAdjustBounds(isRadiusAdjustBounds)
            }
            return bg
        }
    }
}
