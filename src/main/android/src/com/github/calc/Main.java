package com.github.calc;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Activity {
    private BigDecimal currentValue = BigDecimal.ZERO;
    private BigDecimal savedValue = BigDecimal.ZERO;
    private boolean initValue = true;
    private boolean doInitValue = true;
    private char commandCode = '=';
    private char mnemonic = 'D';
    private BigDecimal memoryValue = BigDecimal.ZERO;
    private String text;
    private String topText = "";
    private final String template =
"<html>"
+ "  <head>"
+ "  </head>"
+ "  <body>"
+ "    <p style=\"text-align:right;font-size:10px;margin-top: 0\">"
+ "     %s"            
+ "    </p>"
+ "    <p style=\"text-align:right;font-size:14px;margin-top: 0\">"
+ "     %s"
+ "    </p>"
+ "  </body>"
+ "</html>";
    EditText editText;
    
    Button button0,button1,button2,button3,
                button4,button5,button6,button7,button8,button9,buttonPlus,buttonMinus,buttonMultiply,
                buttonDivide,buttonEqual,buttonPoint,buttonDel,buttonReset,
                button_sin,button_cos,button_tan,button_squared_2,button_root,button_del,button_dec,
                button_bin,button_pi;
    
    String sum="",one,oneAgain="",two,twoAgain="",three,threeAgain="",four,fourAgain="",five,fiveAgain="",
                            six,sixAgain,seven,sevenAgain="",eight,eightAgain="",nine,nineAgain="",
                            zero,plus,minus,multiply,divide,equal,point,del,reset,
                            dec_string="",hex_string="",oct_string="",pi="3.1416";
    
    Integer countOne=0,dec_num,unicode_value;
    
    Float result=0f,result_mul=1f,result_div=1f;
    
    int pressCount=1,sumZero,dec_flag=0,c,i;

    char press;
    
    String EditTextMsg,bin_num,hex_num,oct_num;
    
    Float floatEditTextMsg;
    
    Double doubleEditTextMsg,afterSin,after_cos,after_tan,toRadian_doubleEditTextMsg,after_squared_2,after_root,after_qube;
    
    Vibrator vibrator;
       
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        editText=(EditText)findViewById(R.id.editText1);

        button0=(Button)findViewById(R.id.button0);
        button1=(Button)findViewById(R.id.button1);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button4=(Button)findViewById(R.id.button4);
        
        button5=(Button)findViewById(R.id.button5);
        button6=(Button)findViewById(R.id.button6);
        button7=(Button)findViewById(R.id.button7);
        button8=(Button)findViewById(R.id.button8);
        button9=(Button)findViewById(R.id.button9);
        
        buttonPlus=(Button)findViewById(R.id.buttonPlus);
        buttonMinus=(Button)findViewById(R.id.buttonMinus);
        buttonMultiply=(Button)findViewById(R.id.buttonMultiply);
        buttonDivide=(Button)findViewById(R.id.buttonDivide);
        buttonPoint=(Button)findViewById(R.id.buttonPoint);
        
        buttonEqual=(Button)findViewById(R.id.buttonEqual);
        
        button_sin=(Button)findViewById(R.id.button_sin);
        button_cos=(Button)findViewById(R.id.button_cos);
        button_tan=(Button)findViewById(R.id.button_tan);
        button_root=(Button)findViewById(R.id.button_root);
        button_squared_2=(Button)findViewById(R.id.button_squared_2);
        
        button_del=(Button)findViewById(R.id.button_del);
        button_dec=(Button)findViewById(R.id.button_dec);
        button_bin=(Button)findViewById(R.id.button_bin);
        
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
        
        initCalc();
    }
    
    public void onClickListener0(View v)
    {
        vibrator.vibrate(30);
        keyDetect('0');
    }
    
    public void onClickListener1(View v)
    {
        vibrator.vibrate(30);
        keyDetect('1');
    }
    
    public void onClickListener2(View v)
    {
        vibrator.vibrate(30);
        keyDetect('2');
    }
    
    public void onClickListener3(View v)
    {
        vibrator.vibrate(30);
        keyDetect('3');
    }
    
    public void onClickListener4(View v)
    {
        vibrator.vibrate(30);
        keyDetect('4');
    }
    
    public void onClickListener5(View v)
    {
        vibrator.vibrate(30);
        keyDetect('5');
    }
    
    public void onClickListener6(View v)
    {
        vibrator.vibrate(30);
        keyDetect('6');
    }
    
    public void onClickListener7(View v)
    {
        vibrator.vibrate(30);
        keyDetect('7');
    }
    
    public void onClickListener8(View v)
    {
        vibrator.vibrate(30);
        keyDetect('8');
    }
    
    public void onClickListener9(View v)
    {
        vibrator.vibrate(30);
        keyDetect('9');
    }
    
    public void onClickListenerPlus(View v)
    {
        vibrator.vibrate(30);
        fCalc("+");
    }
    
    public void onClickListenerMinus(View v)
    {
        vibrator.vibrate(30);
        fCalc("-");
    }
    
    public void onClickListenerMultiply(View v)
    {
        fCalc("*");
    }
    
    
    public void onClickListenerDivide(View v)
    {
        vibrator.vibrate(30);
        fCalc("/");
    }
    
    public void onClickListenerPoint(View v)
    {
        vibrator.vibrate(30);
        keyDetect(',');
    }
    
    public void onClickListenerEqual(View v)
    {
        vibrator.vibrate(30);
        fCalc("=");
    }
    
    public void onClickListenerExit(View v)
    {
        vibrator.vibrate(30);
        finish();
    }
    
    public void onClickListenerReset(View v)
    {
        vibrator.vibrate(30);
        fCalc("ce");
    }
    
    
    public void onClickListener_sin(View v)
    {
        vibrator.vibrate(30);
        fCalc("sin");
    }
    
    public void onClickListener_cos(View v)
    {
        vibrator.vibrate(30);
        fCalc("cos");
    }
    
    public void onClickListener_tan(View v)
    {
        vibrator.vibrate(30);
        fCalc("tan");
    }
    
    
    public void onClickListener_squared_2(View v)
    {
        vibrator.vibrate(30);
        fCalc("sqr");
    }
    
    public void onClickListener_qube(View v)
    {
        vibrator.vibrate(30);
        fCalc("cube");
    }
    
     public void onClickListener_root(View v)
    {
         vibrator.vibrate(30);
        fCalc("sqrt");
    }
     
     public void onClickListener_pi(View v)
     {
          vibrator.vibrate(30);
          addCalc("3,1415926535897932384626433832795");
     } 
     
     public void onClickListener_del(View v)
     {
         vibrator.vibrate(30);
         fCalc("nbs");
     }
     
     public void onClickListener_dec(View v)
     {
         vibrator.vibrate(30);
         
         EditTextMsg= editText.getText().toString();
         
         for(int i=0;i<=EditTextMsg.length()-1;i++)
         {
             unicode_value=EditTextMsg.codePointAt(i);
             if(unicode_value>49 || unicode_value<48)
             {
                 dec_flag=1;
                 Log.d("uni",unicode_value.toString());
                 break;
             }
             

         }
         
         
         if(dec_flag==0)
         {
             dec_num=Integer.parseInt(EditTextMsg, 2);
             
             
             editText.setText(dec_num.toString());
             
             EditTextMsg=editText.getText().toString();
             
             sum="";
         }
         else
         {
                 
                 editText.setText("input error");
                 
                 sum="";
         }
             
     } 
    
     public void onClickListener_bin(View v)
     {
         vibrator.vibrate(30);
         //button_bin.setBackgroundColor(Color.BLUE);
         EditTextMsg= editText.getText().toString();
         
         for(i=0;i<EditTextMsg.length();i++)
         {
             if(EditTextMsg.charAt(i)=='.')
             {
                 break;
             }
             else
             {
                 dec_string=dec_string+EditTextMsg.charAt(i);
             }
         }
         dec_num=Integer.parseInt(dec_string);
         
         Log.d("dec_num=",dec_num.toString());
         
         bin_num=Integer.toBinaryString(dec_num);
         
         editText.setText(bin_num);
         
         dec_string="";
         EditTextMsg="";
         bin_num="";
         sum="";
         
     }
     
     public void onClickListener_hex(View v)
     {
         vibrator.vibrate(30);
         
         EditTextMsg= editText.getText().toString();
         
         for(i=0;i<EditTextMsg.length();i++)
         {
             if(EditTextMsg.charAt(i)=='.')
             {
                 break;
             }
             else
             {
                 hex_string=hex_string+EditTextMsg.charAt(i);
             }
         }
         dec_num=Integer.parseInt(hex_string);
         
         Log.d("dec_num=",dec_num.toString());
         
         hex_num=Integer.toHexString(dec_num);
         
         editText.setText(hex_num);
         
         dec_string="";
         hex_string="";
         
         EditTextMsg="";
         
         bin_num="";
         hex_num="";
         
         sum="";
         
     }
     
     public void onClickListener_oct(View v)
     {
         vibrator.vibrate(30);
         
         EditTextMsg= editText.getText().toString();
         
         for(i=0;i<EditTextMsg.length();i++)
         {
             if(EditTextMsg.charAt(i)=='.')
             {
                 break;
             }
             else
             {
                 oct_string=oct_string+EditTextMsg.charAt(i);
             }
         }
         dec_num=Integer.parseInt(oct_string);
         
         Log.d("dec_num=",dec_num.toString());
         
         oct_num=Integer.toOctalString(dec_num);
         
         editText.setText(oct_num);
         
         dec_string="";
         hex_string="";
         oct_string="";
         
         EditTextMsg="";
         
         bin_num="";
         hex_num="";
         oct_num="";
         
         sum="";
         
     }

    private void keyDetect(char key) {
        if (key >= '0' && key <= '9') {
            addCalc("" + key);
        } else if (key == ',') {
            if (initValue || !getText().contains(",")) {
                addCalc("" + key);
            }
        } else if (key == '\u2190') {
            fCalc("nbs");
        }
    }

    private void setText(String text) {
        this.text = text;
        editText.setText(text);
    }

    private String getText() {
        return text;
    }

    private void setTopText(String topText) {
        this.topText += this.topText.equals("") ? topText : (" " + topText);
        setText(getText());
    }

    private void addCalc(String key) {
        if (initValue) {
            if (key.equals(",")) {
                setText("0" + key);
            } else {
                setText(key);
            }
        } else {
            setText(getText() + key);
        }
        if (commandCode == '=') {
            savedValue = new BigDecimal(getText().replace(',', '.'));
            currentValue = BigDecimal.ZERO;
        } else {
            currentValue = new BigDecimal(getText().replace(',', '.'));
        }
        initValue = false;
    }

    private void initCalc() {
        currentValue = BigDecimal.ZERO;
        savedValue = BigDecimal.ZERO;
        initValue = true;
        doInitValue = true;
        commandCode = '=';
        topText = "";
        setText("0");
    }

    private void fCalc(String command) {
        if("ce".equals(command)) {
            initCalc();
        } else if ("=".equals(command)) {
            if (commandCode != '=' && !initValue) {
                BigDecimal value = new BigDecimal(getText().replace(',', '.'));
                BigDecimal result = calcResult(value);
                commandCode = '=';
                this.topText = "";
                setText(result.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("0+$", "").replaceFirst(",$", ""));
                savedValue = result;
                currentValue = BigDecimal.ZERO;
            }
        } else if ("+-".equals(command)) {
                currentValue = new BigDecimal(getText().replace(',', '.'));
                currentValue = currentValue.multiply(new BigDecimal("-1"));
                setText(currentValue.toString().replace('.', ','));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = false;
        } else if("sqrt".equals(command)) {
                currentValue = new BigDecimal(getText().replace(',', '.'));
                try {
                    currentValue = BigDecimalUtil.sqrt(currentValue);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("sqr".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                try {
                    if (currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = currentValue.pow(2);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("ln".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                try {
                    if (currentValue.compareTo(BigDecimal.ZERO) < 0 ||
                        currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = BigDecimalUtil.ln(currentValue, 32);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("log".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                try {
                    if (currentValue.compareTo(BigDecimal.ZERO) < 0 ||
                        currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = BigDecimalUtil.log10(currentValue);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("sin".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                switch (mnemonic) {
                    case 'D':
                        currentValue = currentValue.multiply(BigDecimalUtil.PI_DIV_180);
                        break;
                    case 'G':
                        currentValue = currentValue.multiply(BigDecimalUtil.PI_DIV_200);
                        break;
                    default:
                        break;
                }                
                try {
                    if (currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = BigDecimalUtil.sine(currentValue);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("cos".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                switch (mnemonic) {
                    case 'D':
                        currentValue = currentValue.multiply(BigDecimalUtil.PI_DIV_180);
                        break;
                    case 'G':
                        currentValue = currentValue.multiply(BigDecimalUtil.PI_DIV_200);
                        break;
                    default:
                        break;
                }                
                try {
                    if (currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = BigDecimalUtil.cosine(currentValue);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("tan".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                switch (mnemonic) {
                    case 'D':
                        currentValue = currentValue.multiply(BigDecimalUtil.PI_DIV_180);
                        break;
                    case 'G':
                        currentValue = currentValue.multiply(BigDecimalUtil.PI_DIV_200);
                        break;
                    default:
                        break;
                }                
                try {
                    if (currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = BigDecimalUtil.tangent(currentValue);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("cube".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                try {
                    if (currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = currentValue.pow(3);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("cuberoot".equals(command)) {            
             currentValue = savedValue == BigDecimal.ZERO
                ? new BigDecimal(getText().replace(',', '.')) : savedValue;    
            try {
                if (currentValue.toBigInteger().toString().length() > 256) {
                    initCalc();
                    setText("Error.");
                    return;
                }
                currentValue = BigDecimalUtil.cuberoot(currentValue);
            } catch (ArithmeticException ex) {
                ex.getMessage();
            }
            setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
            if (commandCode == '=') {
                savedValue = currentValue;
                currentValue = BigDecimal.ZERO;
            }
            doInitValue = true;
        } else if ("pow".equals(command)) {
            if (commandCode != '=' && !initValue) {
                BigDecimal value = new BigDecimal(getText().replace(',', '.'));
                BigDecimal result = BigDecimalUtil.pow(savedValue, value);
                setText(result.toString().replace('.', ','));
                savedValue = result;
                currentValue = BigDecimal.ZERO;
            }
            commandCode = '^';
            setText(getText() + " " + commandCode);
        } else if ("yroot".equals(command)) {
            if (commandCode != '=' && !initValue) {
                BigDecimal value = new BigDecimal(getText().replace(',', '.'));
                BigDecimal result = BigDecimalUtil.pow(savedValue, BigDecimal.ONE.divide(value, 32, BigDecimal.ROUND_HALF_UP));
                setText(result.toString().replace('.', ','));
                savedValue = result;
                currentValue = BigDecimal.ZERO;
            }
            commandCode = 'r';
            setTopText(getText() + " " + commandCode);
        } else if("arcsin".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                try {
                    if (currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = BigDecimalUtil.asin(currentValue);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                switch (mnemonic) {
                    case 'D':
                        currentValue = currentValue.divide(BigDecimalUtil.PI_DIV_180, 32, BigDecimal.ROUND_HALF_UP);
                        break;
                    case 'G':
                        currentValue = currentValue.divide(BigDecimalUtil.PI_DIV_200, 32, BigDecimal.ROUND_HALF_UP);
                        break;
                    default:
                        break;
                }                
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("arccos".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                try {
                    if (currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = BigDecimalUtil.acos(currentValue);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                switch (mnemonic) {
                    case 'D':
                        currentValue = currentValue.divide(BigDecimalUtil.PI_DIV_180, 32, BigDecimal.ROUND_HALF_UP);
                        break;
                    case 'G':
                        currentValue = currentValue.divide(BigDecimalUtil.PI_DIV_200, 32, BigDecimal.ROUND_HALF_UP);
                        break;
                    default:
                        break;
                }                
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if("arctan".equals(command)) {            
                currentValue = new BigDecimal(getText().replace(',', '.'));
                try {
                    if (currentValue.toBigInteger().toString().length() > 256) {
                        initCalc();
                        setText("Error.");
                        return;
                    }
                    currentValue = BigDecimalUtil.atan(currentValue);
                } catch (ArithmeticException ex) {
                    ex.getMessage();
                }
                switch (mnemonic) {
                    case 'D':
                        currentValue = currentValue.divide(BigDecimalUtil.PI_DIV_180, 32, BigDecimal.ROUND_HALF_UP);
                        break;
                    case 'G':
                        currentValue = currentValue.divide(BigDecimalUtil.PI_DIV_200, 32, BigDecimal.ROUND_HALF_UP);
                        break;
                    default:
                        break;
                }                
                setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                if (commandCode == '=') {
                    savedValue = currentValue;
                    currentValue = BigDecimal.ZERO;
                }
                doInitValue = true;
        } else if ("nbs".equals(command)) {
            if (!initValue && getText().matches("[\\d,]+")) {
                if (getText().length() == 1) {
                    setText("0");
                    initValue = true;
                } else {
                    setText(getText().substring(0, getText().length() - 1));
                }
                if (commandCode == '=') {
                    savedValue = new BigDecimal(getText().replace(',', '.'));
                } else {
                    currentValue = new BigDecimal(getText().replace(',', '.'));
                }
                return;
            }
        } else if ("+".equals(command)) {
            String saveText = getText();
            if (commandCode != '=' && !initValue) {
                BigDecimal value = new BigDecimal(getText().replace(',', '.'));
                BigDecimal result = calcResult(value);
                setText(result.toString().replace('.', ','));
                savedValue = result;
                currentValue = BigDecimal.ZERO;
            }
            commandCode = '+';
            setTopText(saveText + " " + commandCode);
        } else if ("-".equals(command)) {
            String saveText = getText();
            if (commandCode != '=' && !initValue) {
                BigDecimal value = new BigDecimal(getText().replace(',', '.'));
                BigDecimal result = calcResult(value);
                setText(result.toString().replace('.', ','));
                savedValue = result;
                currentValue = BigDecimal.ZERO;
            }
            commandCode = '-';
            setTopText(saveText + " " + commandCode);
        } else if ("*".equals(command)) {
            String saveText = getText();
            if (commandCode != '=' && !initValue) {
                BigDecimal value = new BigDecimal(getText().replace(',', '.'));
                BigDecimal result = calcResult(value);
                setText(result.toString().replace('.', ','));
                savedValue = result;
                currentValue = BigDecimal.ZERO;
            }
            commandCode = '*';
            setTopText(saveText + " " + commandCode);
        } else if ("/".equals(command)) {
            String saveText = getText();
            if (commandCode != '=' && !initValue) {
                BigDecimal value = new BigDecimal(getText().replace(',', '.'));
                BigDecimal result = calcResult(value);
                setText(result.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                savedValue = result;
                currentValue = BigDecimal.ZERO;
            }
            commandCode = '/';
            setTopText(saveText + " " + commandCode);
        } else if ("1/x".equals(command)) {
            currentValue = savedValue == BigDecimal.ZERO
                ? new BigDecimal(getText().replace(',', '.')) : savedValue;
            try {
                currentValue = BigDecimal.ONE.divide(currentValue, 32, BigDecimal.ROUND_HALF_UP);
            } catch (ArithmeticException ex) {
                ex.getMessage();
            }
            setText(currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
            if (commandCode == '=') {
                savedValue = currentValue;
                currentValue = BigDecimal.ZERO;
            }
            doInitValue = true;
        } else if ("%".equals(command)) {
            if (commandCode != '=' && !initValue) {
                BigDecimal value = new BigDecimal(getText().replace(',', '.'));
                BigDecimal result = savedValue.multiply(value).divide(BigDecimal.valueOf(100), 32, BigDecimal.ROUND_HALF_UP);
                setText(result.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                    .replaceFirst("(.+?)0+$", "$1").replaceFirst(",$", ""));
                currentValue = result;
                return;
            }
         } else if ("MC".equals(command)) {
             memoryValue = BigDecimal.ZERO;
             doInitValue = true;
         } else if ("MR".equals(command)) {
             setText(memoryValue.toPlainString().replace('.', ','));
             if (commandCode == '=') {
                 savedValue = memoryValue;
                 currentValue = BigDecimal.ZERO;
                 doInitValue = true;
             } else {
                 currentValue = memoryValue;
                 doInitValue = false;
                 initValue = false;
             }
         } else if ("MS".equals(command)) {
             memoryValue = new BigDecimal(getText().replace(',', '.'));
             doInitValue = true;
         } else if ("M+".equals(command)) {
             currentValue = new BigDecimal(getText().replace(',', '.'));
             memoryValue = memoryValue.add(currentValue);
             doInitValue = true;
         } else if ("M-".equals(command)) {
             currentValue = new BigDecimal(getText().replace(',', '.'));
             memoryValue = memoryValue.subtract(currentValue);
             doInitValue = true;
         }
        if (doInitValue) {
            initValue = true;
        } else {
            doInitValue = true;
        }
    }

    private BigDecimal calcResult(BigDecimal value) {
        BigDecimal result = BigDecimal.ZERO;
        switch (commandCode) {
        case '+':
            result = savedValue.add(value);
            break;
        case '-':
            result = savedValue.subtract(value);
            break;
        case '*':
            result = savedValue.multiply(value);
            break;
            case '/':
                try {
                    result = savedValue.divide(value, 32, BigDecimal.ROUND_HALF_UP);
                } catch (ArithmeticException ex) {
                    initCalc();
                    setText("Error.");
                    return result;
                }
                break;
            case '^':
                try {
                    result = BigDecimalUtil.pow(savedValue, value);
                } catch (ArithmeticException ex) {
                    initCalc();
                    setText("Error.");
                    return result;
                }
                break;
            case 'r':
                try {
                    result = BigDecimalUtil.pow(savedValue, BigDecimal.ONE.divide(value, 32, BigDecimal.ROUND_HALF_UP));
                } catch (ArithmeticException ex) {
                    initCalc();
                    setText("Error.");
                    return result;
                }
                break;
        }
        return result;
    }
    
}
