
p = ProxySpace.push(s.boot); 

~root = 200*(0).midiratio;

~pulses = { (Impulse.ar(1.1) + Impulse.ar(1.1, phase: 0.5, mul: 0.1) + Impulse.ar(1.1, phase: pi*0.659, mul: 0.05)) * 0.3 };
~fpulses = { Ringz.ar(~pulses.ar(1), ~root * 0.midiratio * 0.midiratio * [-12,3,7,9+12].midiratio * [1,1.5,1/2], 0.3).dup * 0.4 };
~y = { FreeVerb.ar(~fpulses.ar(2), 0.3, 0.8, 20) * 2 }

~y.play;

~bass = {RLPF.ar(VarSaw.ar(~root/4*0.midiratio, width: LFNoise2.ar(12).exprange(0.50,0.6)), VarSaw.ar(1.1/2, width: 0.1).exprange(30,20000), 0.8).dup * 0.8};
~bass.play;
~bass.stop;



SynthDef(\dubecho,{|length = 1, fb = 0.8, sep = 0.012|
var input = In.ar(0, 2);
var output = 0;
length = MouseX.kr(1/3,1/4);
output = input + Fb({

arg feedback; // this will contain the delayed output from the Fb unit

var left,right;
var magic = LeakDC.ar(feedback*fb + input);
magic = HPF.ar(magic, 400); // filter's on the feedback path
magic = LPF.ar(magic, 5000);
magic = magic.tanh; // and some more non-linearity in the form of distortion
#left, right = magic; // let's have named variables for the left and right channels
magic = [DelayC.ar(left, 1, LFNoise2.ar(12).range(0,sep)), DelayC.ar(right, 1, LFNoise2.ar(12).range(sep,0))]; // In addition to the main delay handled by the feedback quark, this adds separately modulated delays to the left and right channels, which with a small "sep" value creates a bit of spatialization

},length);
ReplaceOut.ar(0, output);
}).store;

// Example Usage
~echo = Synth(\dubecho, [\length, 1.1*(1/4), \fb, 0.7, \sep, 0.0012], addAction: \addToTail);

