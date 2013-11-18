SynthDef(\dubecho,{|length = 1, fb = 0.8, sep = 0.012, gate = 0|
var input = In.ar(0, 2);
var output = input + Fb({

arg feedback; // this will contain the delayed output from the Fb unit

var left,right;
var magic = LeakDC.ar(feedback*fb + input);
magic = HPF.ar(magic, 400); // filter's on the feedback path
magic = LPF.ar(magic, 5000);
magic = magic.tanh; // and some more non-linearity in the form of distortion
#left, right = magic; // let's have named variables for the left and right channels
magic = [DelayC.ar(left, 1, LFNoise2.ar(12).range(0,sep)), DelayC.ar(right, 1, LFNoise2.ar(12).range(sep,0))];

},length);
ReplaceOut.ar(0, output);
}).store;

// Example Usage
~echo = Synth(\dubecho, [\length, TempoClock.default.tempo*(3/8), \fb, 0.7, \gate, 1, \sep, 0.0012], addAction: \addToTail);
~echo.free;
~echo.set(\gate, 0);
