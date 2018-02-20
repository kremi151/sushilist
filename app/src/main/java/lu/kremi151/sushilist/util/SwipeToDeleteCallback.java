package lu.kremi151.sushilist.util;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import lu.kremi151.sushilist.R;

/**
 * Created by michm on 18.02.2018.
 */

public abstract class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final int swipeToDeleteColor;
    private final Paint paint;
    private final Drawable deleteIcon;
    private final float iconMargin;

    public SwipeToDeleteCallback(Resources resources) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.swipeToDeleteColor = resources.getColor(R.color.colorSwipeToDelete);
        this.deleteIcon = resources.getDrawable(R.drawable.ic_action_delete, null);
        this.iconMargin = resources.getDimension(R.dimen.swipeDeleteIconMargin);
        this.paint = new Paint();
    }

    protected abstract void onRemove(int position);

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
        onRemove(viewHolder.getAdapterPosition());
    }

    private int calculateDeleteBackgroundColorAlpha(float dX, float width){
        return (int)(Math.min(Math.abs(2f * dX) / width, 1.0f) * 255f);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            View itemView = viewHolder.itemView;

            paint.setColor(swipeToDeleteColor);
            paint.setAlpha(calculateDeleteBackgroundColorAlpha(dX, itemView.getRight() - itemView.getLeft()));
            if (dX > 0) {
                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom(), paint);
                //Not working
                /*if(dX > iconMargin){
                    c.save();
                    final Rect oldBounds = deleteIcon.copyBounds();
                    final float iconY = ((itemView.getBottom() - itemView.getTop()) - oldBounds.height()) / 2f;
                    c.translate(itemView.getLeft() + iconMargin, iconY);
                    deleteIcon.setBounds(oldBounds.left, oldBounds.top, Math.min((int) dX, oldBounds.width()), oldBounds.bottom);
                    deleteIcon.draw(c);
                    deleteIcon.setBounds(oldBounds);
                    c.restore();
                }*/
            } else if(dX < 0){
                c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom(), paint);
                //Not working
                /*if(-dX > iconMargin){
                    c.save();
                    final Rect oldBounds = deleteIcon.copyBounds();
                    final float iconY = ((itemView.getBottom() - itemView.getTop()) - oldBounds.height()) / 2f;
                    c.translate((float) itemView.getRight() + dX - iconMargin, iconY);
                    deleteIcon.setBounds(Math.max(oldBounds.width() + (int) dX, 0), oldBounds.top, oldBounds.right, oldBounds.bottom);
                    deleteIcon.draw(c);
                    deleteIcon.setBounds(oldBounds);
                    c.restore();
                }*/
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }
}
