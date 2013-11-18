(
// We define a group of Patters  to use for parameters to the instrument. Same as Step 1 above.
~a = Prand([50-12,57-12,50,53,57,60,50+12,53+12,57+12,59+12].midicps, inf);
~b = Prand([1/8, 1/2, 1, 1/16]/2, inf); 

// a SynthDef to play i.e. the instrument
SynthDef(\test, { | out, freq = 440, amp = 0.1, nharms = 10, pan = 0, gate = 1 |
    var newfreq = (freq.cpsmidi + LFNoise2.ar(12).range(-1/6,1/6)).midicps;
    var wmod = Linen.kr(gate, attackTime: 0.1, releaseTime: 1);
    var audio = Pulse.ar(newfreq, amp/5, width: wmod.range(0.5,0.55)) + Pulse.ar(newfreq*2, amp/5, width: wmod.range(0.5,0.52)) + VarSaw.ar(newfreq/2, mul: 0.8, width: wmod.range(0.5,0.4));
    var env = Linen.kr(gate, attackTime: 0.001, releaseTime: 0.01, doneAction: 2);
    var sweep = Linen.kr(gate, attackTime: 1/2, releaseTime: 0.1).range(20000,freq);
    audio = MoogLadder.ar(audio, sweep, 0.03) * (1/2);
    audio = [DelayC.ar(audio , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(audio , 1, LFNoise2.ar(1/2).range(0.012,0))];
    OffsetOut.ar(out, Pan2.ar(audio, pan, env) );
}).memStore;
)

// Now we can map the Patters to some of the parameters of the SynthDef (as "Keys" of the event)
(
Pbind(
    \instrument, \test,     // use the /test SynthDef
    \freq, ~a,           // our Pseq from above
    \dur, ~b         // our Prand from above
).play;
)   
