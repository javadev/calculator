module( "tests", {
    setup: function() {
        this.commonValue = "123";
    }
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
    equal($id().value, "5,1", "Value must be 5.1, but " + $id().value);
});
