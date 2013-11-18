(
SynthDef(\scansynth1, { arg out=0, amp=0.1, gate=1, pan=0, freq=200;
    var in, ou;
    var spring = (0.1).dup(35);
    var damp = (0.0564812).dup(35);
    var springs;
    var sel;
    in = LPF.ar(LocalIn.ar(1), XLine.ar(1,3,0.3)) * 0.97 + (LPF.ar(Trig.ar(gate, 0.01),200) * WhiteNoise.ar.range(0.98,1));
    ou = in;
    springs = spring.collect { arg spr, i;
        ou = Spring.ar(ou, spr, damp[i]);
    };
    LocalOut.ar(ou);
    sel = VarSaw.ar(freq * [0.999,1,3/2,3.001/2,0.499,2,1.01] * 0.5, width: LFNoise2.ar(1/2).range(0.2,0.5)).range(0, spring.size-1);
    ou = Splay.ar(SelectX.ar(sel, springs), 1);
    ou = ou * EnvGen.ar(\adsr.kr(Env.adsr(0.3,0.1,1.1,0.1)),gate,doneAction:2);
    ou = Pan2.ar(ou, pan, amp);
    Out.ar(out, ou * 0.7);
}).add;
)

SynthDef(\pip, { | freq = 440 |
Out.ar(0, XLine.ar(1,1/1000, 0.4, doneAction: 2) * SinOsc.ar(freq, phase: XLine.ar(1,1/1000,0.1) * SinOsc.ar(freq * Rand(1,6).round(1) / Rand(1,3).round(1))).dup * 0.2);
}).add;

TempoClock.default.tempo = 1.1
~echo = Synth(\dubecho, [\length, TempoClock.default.tempo*(1/3), \fb, 0.7, \sep, 0.0012], addAction: \addToTail);
~echo.free;
(
Pdef(\plop, Ptpar([
0,Pbind(
    \instrument, \scansynth1,
    \scale, Scale.minor,
    \degree, Pseq([0,2,4,-1],inf) + [0,2,-12,4],
    \strum, 3/32,
    \dur, 4,
    \amp, 2,
    \legato, 1.01
),
16,Pbind(
\instrument, \pip,
\degree, Pstep([0,-3],4,inf) + Pseq([0,7,3],inf),
\octave, Pwrand([5,6],[3,1],inf),
\dur, Pseq([1,[3,[3,[3,1]],2]].convertRhythm,inf),
)])
).play;
)
