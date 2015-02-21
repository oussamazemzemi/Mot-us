
package com.example.android.wifidirect.discovery;

import and.ecl.motus.Dictionnaire;
import and.ecl.motus.Dictionnairesimple;
import and.ecl.motus.R;
import and.ecl.motus.start;
import and.ecl.motus.FragmentMotusActivitysimple.ChatMessageAdapter;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This fragment handles chat related UI which includes a list view for messages
 * and a message entry field with send button.
 */
public class WiFiChatFragment extends Fragment {
	//oussama var
	public int min = 0;
	public static int sec = 10;
	
	public TableLayout _tl;
	public TableRow _tr;
	public String _chaine=new String();
	public String _conc;
	public String _motajouer;
	
	
	static Integer[] _tableauedittext={
			R.id.EditText01,
			R.id.EditText02,
			R.id.EditText03,
			R.id.EditText04,
			R.id.EditText05,
			R.id.EditText09,
			R.id.EditText10,
			R.id.EditText11,
			R.id.EditText12,
			R.id.EditText13,
			R.id.EditText17,
			R.id.EditText18,
			R.id.EditText19,
			R.id.EditText20,
			R.id.EditText21,
			R.id.EditText25,
			R.id.EditText26,
			R.id.EditText27,
			R.id.EditText28,
			R.id.EditText29,
			R.id.EditText33,
			R.id.EditText34,
			R.id.EditText35,
			R.id.EditText36,
			R.id.EditText37,
			R.id.EditText41,
			R.id.EditText42,
			R.id.EditText43,
			R.id.EditText44,
			R.id.EditText45,
			R.id.EditText49,
			R.id.EditText50,
			R.id.EditText51,
			R.id.EditText52,
			R.id.EditText53,
			R.id.EditText57,
			R.id.EditText58,
			R.id.EditText59,
			R.id.EditText60,
			R.id.EditText61
};
	private String _alphabet="abcdefghijklmnopkrstuvwxyz";
	
	private EditText _editText;
	private static EditText _edittext2;
	public static EditText _edittext1;
	private static int _deb=0;
	private static int _fin=5;
	private ImageView _send;
	private static CharSequence _meschaines;
	private static String _motjouer;
	private String _chosir;

	//fin
    private static View view;
    private static ChatManager chatManager;
    private ListView listView;
    static ChatMessageAdapter adapter = null;
    private List<String> items = new ArrayList<String>();

    //les mot dans la listview
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);
        _edittext1 = (EditText) view.findViewById(R.id.editText1);
        listView = (ListView) view.findViewById(android.R.id.list);
        adapter = new ChatMessageAdapter(getActivity(), android.R.id.text1,
                items);
        listView.setAdapter(adapter);
        _meschaines="";
	    _editText=(EditText) view.findViewById(R.id.EditText01);
	    
	    _send=(ImageView) view.findViewById(R.id.send);
	    
	    _chosir=choisirmot();
	    _editText.setText(_chosir);
	    _motjouer=mot_a_jouer();
				
	    _edittext1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
	    	@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
	                clickToSend(_edittext1);	                
	                return true;
	            }
	            return false;
			}
	    });
	    
	    _send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				clickToSend(_editText);
			}
		});
	    
    	if(start.multiple==true)
    	{
    		//Declare the timer
			myRun();
		
			Log.v("stop time", "stopped");
		}
        
    	return view;
    }

    public interface MessageTarget {
        public Handler getHandler();
    }

    public void setChatManager(ChatManager obj) {
        chatManager = obj;
    }

    public static void pushMessage(String readMessage) {
        adapter.add(readMessage);
        adapter.notifyDataSetChanged();
    }

    /**
     * ArrayAdapter to manage chat messages.
     */
    public class ChatMessageAdapter extends ArrayAdapter<String> {

        List<String> messages = null;

        public ChatMessageAdapter(Context context, int textViewResourceId,
                List<String> items) {
            super(context, textViewResourceId, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(android.R.layout.simple_list_item_1, null);
            }
            String message = items.get(position);
            if (message != null && !message.isEmpty()) {
                TextView nameText = (TextView) v
                        .findViewById(android.R.id.text1);

                if (nameText != null) {
                    nameText.setText(message);
                    if (message.startsWith("Me: ")) {
                        nameText.setTextAppearance(getActivity(),
                                R.style.normalText);
                    } else {
                        nameText.setTextAppearance(getActivity(),
                                R.style.boldText);
                    }
                }
            }
            return v;
        }
    }
    
    //oussama code
    public void myRun() {
    	final Timer t = new Timer();
    	//Set the schedule function and rate
    	t.scheduleAtFixedRate(new TimerTask() {

    		@Override
    		public void run() {
    			getActivity().runOnUiThread(new Runnable() {

    				@Override
    				public void run() {
    					TextView tv = (TextView) view.findViewById(R.id.main_timer_text);
    					TextView tv2 = (TextView) view.findViewById(R.id.main_timer_text2);
    					tv.setText(String.valueOf(sec));
    					tv2.setText(String.valueOf(min));
    					sec -= 1;
    					if(sec<0)
    					{//min -= 1;
    					//sec=60;
    						sec=0;
    						notYou();
    						t.cancel();
    					}
    					/*else if(min==0 && sec!=0)
    					{sec=60;
    					}
    					else if(min==0 && sec==0)
    					{_send.setVisibility(1);
    					_edittext1.setEnabled(false);
    					t.cancel();
    					}*/
    				}
    				
    			});
    		}
    		
    	}, 0, 1000);
    }
    
    public static void clickToSend(EditText _edittext1) {
		
    	if (chatManager != null) {
            chatManager.write(_edittext1.getText().toString()
                    .getBytes());
            
            pushMessage("Me: " + _edittext1.getText().toString());
            Log.v("Me", _edittext1.getText().toString());
           
            _edittext1.clearFocus();
        }
    	
		Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(_edittext1.getText().toString());
		if(ms.matches())
		{
			if(_fin<=40)
				{distribuer(_edittext1);
				colorier(_edittext1,_deb, _fin);
				_deb=_deb+5;
				_fin=_fin+5;
				sec=10;
				}
			else
				{
				/*Toast.makeText(super.getActivity().getBaseContext(), "Game Over" ,Toast.LENGTH_SHORT).show();
				Toast.makeText(super.getActivity().getBaseContext(), "The Word is: "+_motjouer,Toast.LENGTH_LONG).show();*/
				}
			
		}
	
		if(start.multiple==true)
        {sec=10;}
		
		_edittext1.setText("");
	}
    
    protected void restart() {
		Intent i = super.getActivity().getBaseContext().getPackageManager()
		 .getLaunchIntentForPackage(super.getActivity().getBaseContext().getPackageName() );
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );

		 startActivity(i);

		
	}



	public String mot_a_jouer()
	{
		
	if(_chosir.contains(Dictionnairesimple.get_A()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_A()[num];
				
	
	}
	else if(_chosir.contains(Dictionnairesimple.get_B()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_B()[num];
				
	
	}
	else if(_chosir.contains(Dictionnairesimple.get_C()[1].substring(0, 1)))
	{	Random random=new Random(); 
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_C()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_D()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_D()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_E()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_E()[num];
				
	
	}else if(_chosir.contains(Dictionnaire.get_F()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_F()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_G()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_G()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_H()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_H()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_I()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_I()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_J()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_J()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_K()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_K()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_L()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_L()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_M()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_M()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_N()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_N()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_O()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_O()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_P()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_P()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_Q()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_Q()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_R()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_R()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_S()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_S()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_T()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_T()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_U()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_U()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_V()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_V()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_W()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_W()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_X()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_X()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_Y()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_Y()[num];
				
	
	}else if(_chosir.contains(Dictionnairesimple.get_Z()[1].substring(0, 1)))
	{	Random random=new Random();
			int num=random.nextInt(5);
			_motajouer=Dictionnairesimple.get_Z()[num];
				
	
	}
	//a ajouter les autres alphabet
	return _motajouer;
		
	}
	
	public String choisirmot()
	{
		Random random=new Random();
		int num=random.nextInt(_alphabet.length());
		return _alphabet.substring(num,num+1);
		
	}
	
	public static boolean distribuer(EditText _edittext1)
	{
		/*CardFlipActivity flip=new CardFlipActivity();
		flip.flipCard(R.id.EditText01);*/
		
		
		if(_edittext1.getText().length()==5)
		{int h=0;
			for(int i=_deb;i<_fin;i++)
			{
				_meschaines=_edittext1.getText().subSequence(h, h+1);
				_edittext2=(EditText) view.findViewById(_tableauedittext[i]);
				_edittext2.setText(_meschaines);
				h++;
				
			}
		return true;
		}
		else
		{/*Toast.makeText(super.getActivity().getBaseContext(), "Number of caracter should be 5" ,Toast.LENGTH_SHORT).show();*/
		return false;
		}

		
	}
	public static void colorier(EditText _edittext1,int d,int f)
	{if(distribuer(_edittext1)==true)
		{
		if(_motjouer.contains(_edittext1.getText().toString()))
		{
			/*Toast.makeText(super.getActivity().getBaseContext(), "same word !"+_motjouer,Toast.LENGTH_SHORT).show();*/}
		else
		{int k=0;
			for(int i=d;i<f;i++)
			{
				
				_edittext2=(EditText) view.findViewById(_tableauedittext[i]);
				
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
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.quitter:
	    	super.getActivity().finish();
	        return true;
	    case R.id.info:
	    	about();
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	public void about()
    {
	    AlertDialog.Builder builder=new AlertDialog.Builder(super.getActivity().getBaseContext());
		builder.setTitle("CREATED BY");
		builder.setMessage("ZEMZEMI Oussama \n " +
				"E-mail:oussama.zemzemi@gmail.com");
		AlertDialog alert=builder.create();
		alert.show();
    }
	public void notYou()
    {
		ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
		progressBar.setVisibility(View.VISIBLE);
		
		_send.setVisibility(View.GONE);
		_edittext1.setEnabled(false);;
    }
    
}
