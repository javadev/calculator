/*
 * $Id$
 *
 * Copyright (c) 2013 Valentyn Kolesnikov
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

var currentValue = BigDecimal.valueOf(0);
var savedValue = BigDecimal.valueOf(0);
var initValue = true;
var doInitValue = true;
var commandCode = '=';
var memoryValue = BigDecimal.valueOf(0);
function $id() {
    return document.getElementById('calc_result');
}
function fCalc(command) {
    if("ce" == command) {
        initCalc();
    } else if ("=" == command) {
        if (commandCode != '=' && !initValue) {
            var value = new BigDecimal($id().value.replace(',', '.'));
            var result = BigDecimal.valueOf(0);
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
                    } catch (ex) {
                        initCalc();
                        $id().value = "Error.";
                        return;
                    }
                    break;
            }
            commandCode = '=';
            $id().value = result.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                .replace(/0+$/, "").replace(/,$/, "");
            savedValue = result;
            currentValue = BigDecimal.valueOf(0);
        }
    } else if ("+-" == command) {
            currentValue = new BigDecimal($id().value.replace(',', '.'));
            currentValue = currentValue.multiply(new BigDecimal("-1"));
            $id().value = currentValue.toPlainString().replace('.', ',');
            if (commandCode == '=') {
                savedValue = currentValue;
                currentValue = BigDecimal.valueOf(0);
            }
            doInitValue = false;
    } else if("sqrt" == command) {
            currentValue = new BigDecimal($id().value.replace(',', '.'));
            try {
                currentValue = sqrt(currentValue);
            } catch (ex) {
                console.log(ex);
            }
            $id().value = currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                .replace(/(.+?)0+$/, "$1").replace(/,$/, "");
            if (commandCode == '=') {
                savedValue = currentValue;
                currentValue = BigDecimal.valueOf(0);
            }
            doInitValue = true;
    } else if ("nbs" == command) {
        if (!initValue && $id().value.match("[\\d,]+")) {
            if ($id().value.length == 1) {
                $id().value = "0";
                initValue = true;
            } else {
                $id().value = $id().value.substring(0, $id().value.length - 1);
            }
            savedValue = new BigDecimal($id().value.replace(',', '.'));
            return;
        }
    } else if ("+" == command) {
        if (commandCode != '=' && !initValue) {
            var value = new BigDecimal($id().value.replace(',', '.'));
            var result = savedValue.add(value);
            $id().value = result.toPlainString().replace('.', ',');
            savedValue = result;
            currentValue = BigDecimal.valueOf(0);
        }
        commandCode = '+';
    } else if ("-" == command) {
        if (commandCode != '=' && !initValue) {
            var value = new BigDecimal($id().value.replace(',', '.'));
            var result = savedValue.subtract(value);
            $id().value = result.toPlainString().replace('.', ',');
            savedValue = result;
            currentValue = BigDecimal.valueOf(0);
        }
        commandCode = '-';
    } else if ("*" == command) {
        if (commandCode != '=' && !initValue) {
            var value = new BigDecimal($id().value.replace(',', '.'));
            var result = savedValue.multiply(value);
            $id().value = result.toPlainString().replace('.', ',');
            savedValue = result;
            currentValue = BigDecimal.valueOf(0);
        }
        commandCode = '*';
    } else if ("/" == command) {
        if (commandCode != '=' && !initValue) {
            var value = new BigDecimal($id().value.replace(',', '.'));
            var result = savedValue.divide(value, 32, BigDecimal.ROUND_HALF_UP);
            $id().value = result.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                .replace(/(.+?)0+$/, "$1").replace(/,$/, "");
            savedValue = result;
            currentValue = BigDecimal.valueOf(0);
        }
        commandCode = '/';
    } else if ("1/x" == command) {
        currentValue = savedValue == BigDecimal.valueOf(0)
            ? new BigDecimal($id().value.replace(',', '.')) : savedValue;
        try {
            currentValue = BigDecimal.valueOf(1).divide(currentValue, 32, BigDecimal.ROUND_HALF_UP);
        } catch (ex) {
            console.log(ex);
        }
        $id().value = currentValue.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
            .replace(/(.+?)0+$/, "$1").replace(/,$/, "");
        if (commandCode == '=') {
            savedValue = currentValue;
            currentValue = BigDecimal.valueOf(0);
        }
        doInitValue = true;
    } else if ("%" == command) {
        if (commandCode != '=' && !initValue) {
            var value = new BigDecimal($id().value.replace(',', '.'));
            var result = savedValue.multiply(value).divide(BigDecimal.valueOf(100), 32, BigDecimal.ROUND_HALF_UP);
            $id().value = result.setScale(16, BigDecimal.ROUND_HALF_UP).toPlainString().replace('.', ',')
                .replace(/(.+?)0+/, "$1").replace(/,$/, "");
            currentValue = result;
            return;
        }
    } else if ("MC" == command) {
        memoryValue = BigDecimal.valueOf(0);
        doInitValue = true;
    } else if ("MR" == command) {
        $id().value = memoryValue.toPlainString().replace('.', ',');
        if (commandCode != '=') {
            currentValue = BigDecimal.valueOf(0);
            initValue = false;
        }
        doInitValue = false;
    } else if ("MS" == command) {
        currentValue = new BigDecimal($id().value.replace(',', '.'));
        memoryValue = currentValue;
        doInitValue = true;
    } else if ("M+" == command) {
        currentValue = new BigDecimal($id().value.replace(',', '.'));
        memoryValue = memoryValue.add(currentValue);
        doInitValue = true;
    } else if ("M-" == command) {
        currentValue = new BigDecimal($id().value.replace(',', '.'));
        memoryValue = memoryValue.subtract(currentValue);
        doInitValue = true;
    }
    if (doInitValue) {
        initValue = true;
    } else {
        doInitValue = true;
    }
}
function addCalc(command) {
    if (initValue) {
        if (command == ",") {
            $id().value = "0" + command;
        } else {
            $id().value = command;
        }
    } else {
        $id().value = $id().value + command;
    }
    if (commandCode == '=') {
        savedValue = new BigDecimal($id().value.replace(',', '.'));
        currentValue = BigDecimal.valueOf(0);
    } else {
        currentValue = new BigDecimal($id().value.replace(',', '.'));
    }
    initValue = false;
}
function initCalc() {
    currentValue = BigDecimal.valueOf(0);
    savedValue = BigDecimal.valueOf(0);
    initValue = true;
    doInitValue = true;
    commandCode = '=';
    $id().value = "0";
}
function keyDetect(keyChar) {
    if (keyChar >= '0' && keyChar <= '9') {
        addCalc(keyChar);
    } else if (keyChar == ',') {
        if (initValue || $id().value.indexOf(",") == -1) {
            addCalc(keyChar);
        }
    } else if (keyChar == '\u2190') {
        fCalc("nbs");
    }
    return true;
}
function keyDetectCalc(evt) {
    var keyChar = String.fromCharCode(evt.charCode);
    if (keyChar == '+' || keyChar == '-' || keyChar == '*'
            || keyChar == '/' || keyChar == '=' || keyChar == '%') {
        fCalc(keyChar);
    } else if (evt.keyCode == 8) {
        fCalc("nbs");
    } else if (evt.keyCode == 13) {
        fCalc("=");
    } else {
        keyDetect(keyChar == '.' ? ',' : keyChar);
    }
}
function sqrt(x) {
    // Check that x >= 0.
    if (x.signum() < 0) {
        throw new Exception("x < 0");
    }

    // n = x*(10^(2*32))
    var n = x.movePointRight(32 << 1).toBigInteger();

    // The first approximation is the upper half of n.
    var bits = (n.bitLength() + 1) >> 1;
    var ix = n.shiftRight(bits);
    var ixPrev;

    // Loop until the approximations converge
    // (two successive approximations are equal after rounding).
    do {
        ixPrev = ix;

        // x = (x + n/x)/2
        ix = ix.add(n.divide(ix)).shiftRight(1);
    } while (ix.compareTo(ixPrev) != 0);

    return new BigDecimal(ix, 32);
}
