module( "tests", {
    setup: function() {
        this.commonValue = "123";
    }
});

test( "add", function() {
    keyDetect("3");
    keyDetect(",");
    keyDetect("1");
    keyDetect("1");
    fCalc("-");
    keyDetect("3");
    keyDetect(",");
    keyDetect("1");
    fCalc("=");
    equal($id().value, "0,01", "Value must be 0,01, but " + $id().value);
});

test( "div", function() {
    keyDetect("1");
    fCalc("/");
    keyDetect("7");
    fCalc("=");
    equal($id().value, "0,1428571428571429", "Value must be 0,1428571428571429, but " + $id().value);
    fCalc("*");
    keyDetect("7");
    fCalc("=");
    equal($id().value, "1", "Value must be 1, but " + $id().value);
});

test( "sqrt", function() {
    keyDetect('9');
    fCalc("sqrt");
    equal($id().value, "3", "Value must be 3, but " + $id().value);
});

test( "nbs", function() {
    keyDetect('3');
    keyDetect(',');
    keyDetect('1');
    fCalc("+");
    keyDetect('2');
    keyDetect(',');
    keyDetect('1');
    fCalc("nbs");
    fCalc("=");
    equal($id().value, "5,1", "Value must be 5,1, but " + $id().value);
});

test( "comma", function() {
    keyDetect('3');
    keyDetect(',');
    keyDetect('1');
    fCalc("nbs");
    fCalc("+");
    keyDetect('2');
    keyDetect(',');
    keyDetect('0');
    fCalc("=");
    equal($id().value, "5", "Value must be 5, but " + $id().value);
});

test( "plusMinus", function() {
    keyDetect("3");
    keyDetect(",");
    keyDetect("1");
    keyDetect("1");
    fCalc("+");
    keyDetect("2");
    keyDetect(",");
    keyDetect("2");
    fCalc("-");
    keyDetect("3");
    keyDetect(",");
    keyDetect("1");
//        Assert.assertEquals("Value must be 3,11 + 2,2 -, but " + jTextField2,
//            "3,11 + 2,2 -", jTextField2);
    fCalc("=");
    equal($id().value, "2,21", "Value must be 2,21, but " + $id().value);
});
