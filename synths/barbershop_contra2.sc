(
SynthDef(\sawpulse, { |out, freq = 440, gate = 0.5, plfofreq = 6, mw = 0, ffreq = 2000, rq = 0.3, freqlag = 0.05, amp = 1|
    var sig, plfo, fcurve;
    plfo = SinOsc.kr(plfofreq, mul:mw, add:1);
    freq = Lag.kr(freq, freqlag * 8) * plfo * 0.25;
    fcurve = EnvGen.kr(Env.adsr(0.1, 0.1, 0.4, 0.2), gate);
    fcurve = (fcurve - 1).madd(0.7, 1) * ffreq;
    sig = LFPulse.ar(freq*[1.001,3/2,1/2.001,1/4,1/2,3.001/2,1]*2, width: LFNoise2.ar(1/2).exprange(0.5,0.4));
    //sig = LPF.ar(sig, fcurve, 0.2) * 3;
    sig = sig * EnvGen.kr(Env.adsr(1, 0.2, 0.8, 0.3), gate, doneAction:2);
    Out.ar(out, Splay.ar(sig * 0.1, 1));
}).add;
)

~a = Synth(\sawpulse, [\freq, 70.midicps]);
~b = Synth(\sawpulse, [\freq, 74.midicps]);
~c = Synth(\sawpulse, [\freq, 77.midicps]);
~d = Synth(\sawpulse, [\freq, 79.midicps]);

~a.set(\freq, (70-3).midicps);
~b.set(\freq, (74-3).midicps);
~c.set(\freq, 77.midicps);
~d.set(\freq, 79.midicps);

~a.free;
~b.free;
~c.free;
~d.free;

TempoClock.default.tempo = 0.4

Ppar([
PmonoArtic(\sawpulse, \dur,  Pseq([Pshuf([1/2,1/2,1/4,1/4,1/4,1/4,1]),2,2], inf) * 1/4, \degree, Pstep([0,3], 4, inf) + Pstep(Pseq([0,2+7,4,6+7]), 1/2, inf) + 2, \octave, 6, \rq, 1 / Pwhite(1,8), \legato, Pstep([0.01, 1.1], 1/4, inf) ),
PmonoArtic(\sawpulse, \dur, Pseq([1/2,1/2,1,1,1,2,1,1], inf) * 1/4, \degree, Pstep([0,3], 4, inf) + Pstep(Pseq([0,2+7,4,6+7]), 1/2, inf), \octave, 6, \rq, 1 / Pwhite(1,8), \legato, Pstep([0.2, 0.8], 1/4, inf) )
]).play;

~echo = Synth(\dubecho, [\length, TempoClock.default.tempo*(3/4), \fb, 0.7, \sep, 0.0012], addAction: \addToTail);
~echo.free;
