SynthDef(\dubecho,{|length = 1, fb = 0.8, sep = 0.012, gate = 0|
var input = In.ar(0, 2);
var output = input + Fb({

arg feedback; // this will contain the delayed output from the Fb unit

// filter and distort the feedback signal.  

// Note that the input signal is fed in here as well:
var left,right;
var magic = LeakDC.ar(feedback*fb + input);
magic = HPF.ar(magic, 400);
magic = LPF.ar(magic, 5000);
magic = magic.tanh;
#left, right = magic;
magic = [DelayC.ar(left, 1, LFNoise2.ar(12).range(0,sep)), DelayC.ar(right, 1, LFNoise2.ar(12).range(sep,0))];

// for fun effects, try changing the 0.8 to something greater than one

},length);
ReplaceOut.ar(0, output);
}).store;

~echo = Synth(\dubecho, [\length, TempoClock.default.tempo*(3/8), \fb, 0.7, \gate, 1, \sep, 0.0012], addAction: \addToTail);

~echo.free;
~echo.set(\gate, 0);
