(
SynthDef(\sawpulse, { |out, freq = 440, gate = 0.5, plfofreq = 6, mw = 0, ffreq = 2000, rq = 0.3, freqlag = 0.05, amp = 1|
    var sig, plfo, fcurve;
    plfo = SinOsc.kr(plfofreq, mul:mw, add:1);
    freq = Lag.kr(freq, freqlag * Rand(1,8)) * plfo * 0.25;
    fcurve = EnvGen.kr(Env.adsr(0.1, 0.1, 0.4, 0.2), gate);
    fcurve = (fcurve - 1).madd(0.7, 1) * ffreq;
    sig = SinOsc.ar(freq*[1.001,3/2,1/2.001,1/4,1/2,3.001/2,1], phase: SinOsc.ar(1/2).range(0.1,2) * SinOsc.ar(freq*2) + (SinOsc.ar(freq*3) * XLine.ar(3,0.001,0.1)));
    sig = RLPF.ar(sig, fcurve / 2, 0.8)
        * EnvGen.kr(Env.adsr(0.01, 0.2, 0.8, 1), gate, doneAction:2)
        * amp;
    Out.ar(out, Splay.ar(sig * 2, 1));
}).add;
)

TempoClock.default.tempo = 1.2

p = PmonoArtic(\sawpulse, \dur, Pseq([1/2,1/2,1,1,1,2,2], inf) * 1/4, \degree, Pstep([0,3], 4, inf) + Pstep(Pshuf([0,2+7,4,6+7]), 1/2, inf), \octave, 6, \rq, 0.8 / Pwhite(1,8), \legato, Pstep([0.01, 1.0], 1/4, inf) ).play;

