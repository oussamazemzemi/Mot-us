package and.ecl.motus;



import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.LayoutInflater.Factory;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MotusActivity extends Activity {

	//shake phone
	private SensorManager mSensorManager;
	private float mAccel; // acceleration apart from gravity
	private float mAccelCurrent; // current acceleration including gravity
	private float mAccelLast; // last acceleration including gravity
	//end shake phone
	
	//public int time = 360;
	public int min = 0;
	public int sec = 15;
	
	public TableLayout _tl;
	public TableRow _tr;
	public String _chaine=new String();
	public String _conc;
	public String _motajouer;
	
	
	Integer[] _tableauedittext={
			R.id.EditText01,
			R.id.EditText02,
			R.id.EditText03,
			R.id.EditText04,
			R.id.EditText05,
			R.id.EditText06,
			R.id.EditText07,
			R.id.EditText08,
			R.id.EditText09,
			R.id.EditText10,
			R.id.EditText11,
			R.id.EditText12,
			R.id.EditText13,
			R.id.EditText14,
			R.id.EditText15,
			R.id.EditText16,
			R.id.EditText17,
			R.id.EditText18,
			R.id.EditText19,
			R.id.EditText20,
			R.id.EditText21,
			R.id.EditText22,
			R.id.EditText23,
			R.id.EditText24,
			R.id.EditText25,
			R.id.EditText26,
			R.id.EditText27,
			R.id.EditText28,
			R.id.EditText29,
			R.id.EditText30,
			R.id.EditText31,
			R.id.EditText32,
			R.id.EditText33,
			R.id.EditText34,
			R.id.EditText35,
			R.id.EditText36,
			R.id.EditText37,
			R.id.EditText38,
			R.id.EditText39,
			R.id.EditText40,
			R.id.EditText41,
			R.id.EditText42,
			R.id.EditText43,
			R.id.EditText44,
			R.id.EditText45,
			R.id.EditText46,
			R.id.EditText47,
			R.id.EditText48,
			R.id.EditText49,
			R.id.EditText50,
			R.id.EditText51,
			R.id.EditText52,
			R.id.EditText53,
			R.id.EditText54,
			R.id.EditText55,
			R.id.EditText56,
			R.id.EditText57,
			R.id.EditText58,
			R.id.EditText59,
			R.id.EditText60,
			R.id.EditText61,
			R.id.EditText62,
			R.id.EditText63,
			R.id.EditText64
};
	private String _alphabet="abcdefghijklmnopkrstuvwxyz";
	
	private EditText _editText,_edittext1,_edittext2;
	
	private int _deb=0;
	private int _fin=8;
	
	private ImageView _send;
	private CharSequence _meschaines;
	private String _motjouer;
	private String _chosir;
    /** Called when the activity is first created. */
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
	    super.onCreate(savedInstanceState);  
	    setContentView(R.layout.motus);
	    
	    
	    /* do this in onCreate */
	    mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	    mAccel = 0.00f;
	    mAccelCurrent = SensorManager.GRAVITY_EARTH;
	    mAccelLast = SensorManager.GRAVITY_EARTH;
	    
	    _meschaines="";
	    _editText=(EditText) findViewById(R.id.EditText01);
	    
	    _send=(ImageView) findViewById(R.id.send);
	    _edittext1=(EditText) findViewById(R.id.editText1);
	    _chosir=choisirmot();
	    _editText.setText(_chosir);
	    _motjouer=mot_a_jouer();
		
	    _edittext1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	    	@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
	                clickToSend();
	                return true;
	            }
	            return false;
			}
	    });
			
	    _send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickToSend();
				
			}

			
		});

//Declare the timer
final Timer t = new Timer();
//Set the schedule function and rate
t.scheduleAtFixedRate(new TimerTask() {

	@Override
	public void run() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				TextView tv = (TextView) findViewById(R.id.main_timer_text);
				TextView tv2 = (TextView) findViewById(R.id.main_timer_text2);
				tv.setText(String.valueOf(sec));
				tv2.setText(String.valueOf(min));
				sec -= 1;
				if(sec==0)
				{min -= 1;
				sec=60;
				}
				else if(min==0 && sec!=0)
				{sec=60;
				}
				else if(min==0 && sec==0)
				{_send.setVisibility(1);
				_edittext1.setEnabled(false);
				t.cancel();
				}
			}
			
		});
	}
	
}, 0, 1000);

	
	}



	

	
	protected void clickToSend() {
		Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(_edittext1.getText().toString());
		if(ms.matches())
		{
			if(_fin<=64)
				{distribuer();
				colorier(_deb, _fin);
				_deb=_deb+8;
				_fin=_fin+8;
				}
			else
				{Toast.makeText(getBaseContext(), "Game Over" ,Toast.LENGTH_SHORT).show();
				Toast.makeText(getBaseContext(), "The Word is: "+_motjouer,Toast.LENGTH_LONG).show();
				}
		}
		
	}






	protected void restart() {
		Intent i = getBaseContext().getPackageManager()
		 .getLaunchIntentForPackage(getBaseContext().getPackageName() );
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );

		 startActivity(i);

		
	}



	public String mot_a_jouer()
	{
		
		
	if(_chosir.contains(Dictionnaire.get_A()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_A()[num];
				
	
	}
	else if(_chosir.contains(Dictionnaire.get_B()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_B()[num];
				
	
	}
else if(_chosir.contains(Dictionnaire.get_C()[1].substring(0, 1)))
	{	Random random=new Random(); 
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_C()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_D()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_D()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_E()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_E()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_F()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_F()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_G()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_G()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_H()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_H()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_I()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_I()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_J()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_J()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_K()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_K()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_L()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_L()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_M()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_M()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_N()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_N()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_O()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_O()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_P()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_P()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_Q()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_Q()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_R()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_R()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_S()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_S()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_T()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_T()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_U()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_U()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_V()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_V()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_W()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_W()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_X()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_X()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_Y()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_Y()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_Z()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnaire.get_Z()[num];
				
	
	}
	//a ajouter les autres alphabet
	return _motajouer;
		
	}
	
	public String choisirmot()
	{
		Random random=new Random();
		int num=random.nextInt(25);
		return _alphabet.substring(num,num+1);
		
	}
	
	public boolean distribuer()
	{
		/*CardFlipActivity flip=new CardFlipActivity();
		flip.flipCard(R.id.EditText01);*/
		
		
		if(_edittext1.getText().length()==8)
		{int h=0;
		for(int i=_deb;i<_fin;i++){
			_meschaines=_edittext1.getText().subSequence(h, h+1);
			_edittext2=(EditText) findViewById(_tableauedittext[i]);
			_edittext2.setText(_meschaines);
			h++;
			
		}
		return true;
		}
		else
		{Toast.makeText(getBaseContext(), "Number of caracter should be 8" ,Toast.LENGTH_SHORT).show();
		return false;
		}

		
	}
	public void colorier(int d,int f)
	{if(distribuer()==true)
		{
		if(_motjouer.contains(_edittext1.getText().toString()))
		{
			Toast.makeText(getBaseContext(), "same word !"+_motjouer,Toast.LENGTH_SHORT).show();}
		else
		{int k=0;
			for(int i=d;i<f;i++)
			{
				
				_edittext2=(EditText) findViewById(_tableauedittext[i]);
				
				if(_motjouer.substring(k, k+1).contains(_edittext2.getText().toString()))
				{_edittext2.setBackgroundResource(R.drawable.boulerouge);}
				else if(!(_motjouer.contains(_edittext2.getText().toString())))
				{_edittext2.setBackgroundResource(R.drawable.boulebleu2);}
				else
				{_edittext2.setBackgroundResource(R.drawable.boulejaune);}
				k++;
				
				
			}
			_edittext1.setText("");
		}
		
	
		}
	
	}
	//Méthode qui se déclenchera lorsque vous appuierez sur le bouton menu du téléphone
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
 
        //Création d'un MenuInflater qui va permettre d'instancier un Menu XML en un objet Menu
        MenuInflater inflater = getMenuInflater();
        //Instanciation du menu XML spécifier en un objet Menu
        inflater.inflate(R.layout.menu, menu);
        setMenuBackground(); 
        //Il n'est pas possible de modifier l'icône d'entête du sous-menu via le fichier XML on le fait donc en JAVA
    	//menu.getItem(0).getSubMenu().setHeaderIcon(R.drawable.option_white);
 
        return true;
     }
	
	private void setMenuBackground() {
		// Log.d(TAG, "Enterting setMenuBackGround");  
        getLayoutInflater().setFactory( new Factory() {  
            

			@Override
			public View onCreateView(String name, Context context,
					AttributeSet attrs) {if ( name.equalsIgnoreCase( "com.android.internal.view.menu.IconMenuItemView" ) ) {
	                    try { // Ask our inflater to create the view  
	                        LayoutInflater f = getLayoutInflater();  
	                        final View view = f.createView( name, null, attrs );  
	                        /* The background gets refreshed each time a new item is added the options menu.  
	                        * So each time Android applies the default background we need to set our own  
	                        * background. This is done using a thread giving the background change as runnable 
	                        * object */
	                        new Handler().post( new Runnable() {  
	                            public void run () {  
	                                // sets the background color   
	                                view.setBackgroundResource( Color.WHITE);
	                                // sets the text color              
	                                ((TextView) view).setTextColor(Color.BLACK);
	                                // sets the text size              
	                                ((TextView) view).setTextSize(18);
	                }
	                        } );  
	                    return view;
	                }
	            catch ( InflateException e ) {}
	            catch ( ClassNotFoundException e ) {}  
	        } 
	        return null;
			}}); 
		
		
	}






	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.quitter:
	        finish();
	        return true;
	    case R.id.info:
	    	about();
	    default:
	        return super.onOptionsItemSelected(item);
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
	
	//shake phone
	 /* put this into your activity class */
	  
	  private final SensorEventListener mSensorListener = new SensorEventListener() {

	    public void onSensorChanged(SensorEvent se) {
	      float x = se.values[0];
	      float y = se.values[1];
	      float z = se.values[2];
	      mAccelLast = mAccelCurrent;
	      mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
	      float delta = mAccelCurrent - mAccelLast;
	      mAccel = mAccel * 0.9f + delta; // perform low-cut filter
	      if(mAccel > 2)
	    	  {
	    	  
	    	  }
	    }

	    public void onAccuracyChanged(Sensor sensor, int accuracy) {
	    	
	    }
	  };

	  @Override
	  protected void onResume() {
	    super.onResume();
	    mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
	  }

	  @Override
	  protected void onPause() {
	    mSensorManager.unregisterListener(mSensorListener);
	    super.onPause();
	  }
	  //end shake phone
}