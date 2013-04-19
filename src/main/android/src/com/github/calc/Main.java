/*
 * $Id$
 *
 * Copyright 2013 Valentyn Kolesnikov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**.
 * @author Valentyn Kolesnikov
 * @version $Revision$ $Date$
 */
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
    private EditText editText;
    private Vibrator vibrator;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        editText = (EditText) findViewById(R.id.screen);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE); 
        initCalc();
    }

    public void ButtonClickHandler(View v) {
        vibrator.vibrate(30);
    	switch(v.getId()){
    		case R.id.button0: keyDetect('0'); break;
    		case R.id.button1: keyDetect('1'); break;
    		case R.id.button2: keyDetect('2'); break;
    		case R.id.button3: keyDetect('3'); break;
    		case R.id.button4: keyDetect('4'); break;
    		case R.id.button5: keyDetect('5'); break;
    		case R.id.button6: keyDetect('6'); break;
    		case R.id.button7: keyDetect('7'); break;
    		case R.id.button8: keyDetect('8'); break;
    		case R.id.button9: keyDetect('9'); break;
    		case R.id.buttonPoint : 
    			keyDetect(','); break;
    		case R.id.buttonAdd: fCalc("+"); break;
    		case R.id.buttonSub : fCalc("-"); break;
    		case R.id.buttonMulti: fCalc("*"); break;
    		case R.id.buttonDiv: fCalc("/"); break;
    		case R.id.buttonSqr: fCalc("sqrt"); break;
    		case R.id.buttonPow: fCalc("pow"); break;
    		case R.id.buttonMod: fCalc("%"); break;
    		case R.id.buttonOnediv: fCalc("1/x"); break;
    		case R.id.buttonExe:	
    			fCalc("="); break;
    		case R.id.buttonDel:
	   			 fCalc("nbs"); break;
    		case R.id.buttonClear:
    			fCalc("ce"); break;
    	}
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
