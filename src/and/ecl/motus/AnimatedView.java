package and.ecl.motus;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.ImageView;

public class AnimatedView extends ImageView{

	private Context mContext;
	int x = -1;
	int y = -1;
	private int xVelocity = 10;
	private int yVelocity = 5;
	
	public AnimatedView(Context context, AttributeSet attrs)  {  
		super(context, attrs);  
		mContext = context;  
		new Handler();
    } 
	
	protected void onDraw(Canvas c) {  
		
		BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.blackball1);  
		BitmapDrawable ball2 = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.blackball2);
	    if (x<0 && y <0) {
	    	x = this.getWidth()/2;
	    	y = this.getHeight()/2;
	    } else {
	    	x += xVelocity;
	    	y += yVelocity;
	    	if ((x > this.getWidth() - ball.getBitmap().getWidth()) || (x < 0)) {
	    		xVelocity = xVelocity*-1;
	    	}
	    	if ((y > this.getHeight() - ball.getBitmap().getHeight()) || (y < 0)) {
	    		yVelocity = yVelocity*-1;
	    	}
	    }
	    c.drawBitmap(ball.getBitmap(), x, y, null);
	    c.drawBitmap(ball2.getBitmap(), x+10, y+15, null);
	    //h.postDelayed(r, FRAME_RATE);
			
	    	      
	} 
}
