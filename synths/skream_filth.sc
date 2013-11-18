{
var freq, om, wobble;
wobble = 1/2;
freq = 80.midicps * MouseButton.kr(1,2*(7.midiratio),0.2);
om = Splay.ar(VarSaw.ar(freq * Array.fill(50, { rrand(1.02,0.98); }), width: LFNoise2.ar(1/2).range(0.01,0.1)), 0.8) * (1/5) * AmpComp.kr(freq);
RLPF.ar(om, SinOsc.ar(wobble).exprange(freq,20000)) * SinOsc.ar(wobble, phase: pi).exprange(1,4);
}.play;
