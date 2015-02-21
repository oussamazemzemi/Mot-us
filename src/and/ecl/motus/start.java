package and.ecl.motus;


import java.util.List;
import java.util.concurrent.ExecutionException;



import com.example.android.wifidirect.discovery.WiFiServiceDiscoveryActivity;
import and.db.motus.Alphabet;
import and.db.motus.DatabaseHandler;
import and.db.motus.LoadWebPageAsync;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("CutPasteId")
public class start extends Activity {
	
	MediaPlayer mp;
	private PopupWindow pw;
	TextView q;
	public static boolean flip=false;
	public static boolean multiple=false;
	String _mot;
	
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	    setContentView(R.layout.start);
	  
	    // Inserting Alphabet
        Log.d("Insert: ", "Inserting ..");
	    DatabaseHandler db = new DatabaseHandler(this);
	    db.addAlphabet(new Alphabet(1, "oussama"));
	    
	    // Reading all contacts
        Log.d("Reading: ", "Reading all alphabets..");
        List<Alphabet> alphabet = db.getAllAlphabets();       
 
        for (Alphabet al : alphabet) {
            String log = "Id: "+al.get_id()+" ,Lettre: " + al.get_lettre();
                // Writing Contacts to log
        Log.d("Lettre: ", log);
        
        }
        
        // Reading site
        LoadWebPageAsync task = new LoadWebPageAsync();
        try {
        	String _listemots=" ";
        	
			_listemots=task.execute(new String[] { "http://www.liste-de-mots.com/mots-nombre-lettre/5/a/" }).get();
			
			_listemots.substring(_listemots.indexOf("<p class=\"liste-mots\">"), _listemots.indexOf("bottom-links"));
			
			String[] _mots = new String[_listemots.split(",").length];
			_mots=_listemots.split(",");
			for(int i=0;i<_mots.length;i++)
			{
				Log.d("A: ", _mots[i]);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
	      //flip
          final Animation animation2 = AnimationUtils.loadAnimation(this, R.animator.to_middle);
          final Animation animation1 = AnimationUtils.loadAnimation(this, R.animator.from_middle);
          final ImageView myImageViewFlip2= (ImageView)findViewById(R.id.imageView2);
          final ImageView myImageViewFlip3= (ImageView)findViewById(R.id.imageView3);
          final ImageView myImageViewFlip4= (ImageView)findViewById(R.id.imageView4);
	    
	      /* mp=MediaPlayer.create(this,R.drawable.motusmp3);
	      mp.start();*/
	    
          final ImageView rouge= (ImageView)findViewById(R.id.imageView4);
	      
	      
		  Animation myFadeInAnimation = AnimationUtils.loadAnimation(this, R.animator.anim4);
		  rouge.startAnimation(myFadeInAnimation); //Set animation to your ImageView
		  
		  
		  final ImageView jaune= (ImageView)findViewById(R.id.imageView2);
		  myFadeInAnimation = AnimationUtils.loadAnimation(this, R.animator.anim2);
		  jaune.startAnimation(myFadeInAnimation); //Set animation to your ImageView
		  
		  
		  final ImageView blue= (ImageView)findViewById(R.id.imageView3);
		  myFadeInAnimation = AnimationUtils.loadAnimation(this, R.animator.anim3);
		  blue.startAnimation(myFadeInAnimation); //Set animation to your ImageView
		  
		  		  
		  q=(TextView)findViewById(R.id.q);
		 
		  
		  rouge.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		  });
		  
		  jaune.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				//initiatePopupWindow();
				//about();
				multiple=true;
				Intent myIntent = new Intent(view.getContext(), WiFiServiceDiscoveryActivity.class);
                startActivityForResult(myIntent, 0);
			}
		  });
		  
		  
		  blue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				if(flip == false)
				{
				myImageViewFlip2.startAnimation(animation1);
				myImageViewFlip2.setImageResource(R.drawable.boulerouge);
				//myTextView3.setText("Simple");
				
				blue.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						Intent myIntent = new Intent(view.getContext(), MotusActivitysimple.class);
		                startActivityForResult(myIntent, 0);
					}
				});
				
				myImageViewFlip3.startAnimation(animation1);
				myImageViewFlip3.setImageResource(R.drawable.boulevert);
				//rouge.setText("Super");
				rouge.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						Intent myIntent = new Intent(view.getContext(), MotusActivity.class);
		                startActivityForResult(myIntent, 0);
					}
				});
				
				myImageViewFlip4.startAnimation(animation1);
				myImageViewFlip4.setImageResource(R.drawable.bouleviolet);
				//jaune.setText("Quitter");
				jaune.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View view) {
						finish();
					}
				});
				
				}
				
			}
		});
		  
	}
	

	
	private void initiatePopupWindow() {
	    try {
	        //We need to get the instance of the LayoutInflater, use the context of this activity
	        LayoutInflater inflater = (LayoutInflater) start.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        //Inflate the view from a predefined XML layout
	        View layout = inflater.inflate(R.layout.aide,(ViewGroup) findViewById(R.id.EditText63));
	        // create a 300px width and 470px height PopupWindow
	        pw = new PopupWindow(layout, 150, 200, true);
	        // display the popup in the center
	        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
	 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	public void about()
    {
	    AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("CREATED BY");
		builder.setMessage("ZEMZEMI Oussama \n " +
				"E-mail:oussama.zemzemi@gmail.com");
		AlertDialog alert=builder.create();
		alert.show();
    }
	 
	public void Rate(Context context)
	{
		Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
		Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
		try {
		  startActivity(goToMarket);
		} catch (ActivityNotFoundException e) {
		  Toast.makeText(context, "Couldn't launch the market", Toast.LENGTH_LONG).show();
		}
	}
	
}
