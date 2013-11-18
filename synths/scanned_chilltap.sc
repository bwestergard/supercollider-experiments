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
    ou = ou * EnvGen.ar(\adsr.kr(Env.adsr(0.3,0.1,0.8,0.1)),gate,doneAction:2);
    ou = Pan2.ar(ou, pan, amp);
    Out.ar(out, ou);
}).add;
)

SynthDef(\pip, { | freq = 440 |
Out.ar(0, XLine.ar(1,1/1000, 0.4, doneAction: 2) * SinOsc.ar(freq).dup * 0.4);
}).add;

(
Pdef(\plop, Ppar([
Pbind(
    \instrument, \scansynth1,
    \scale, Scale.minor,
    \degree, Pseq([0,2,4,-1],inf) + [0,2,4,-12],
    \strum, 3/8,
    \dur, 3,
    \amp, 2,
    \legato, 0.8
),
Pbind(
\instrument, \pip,
\degree, Pstep([0,3],3,inf),
\dur, Pstep([3/16,3/32],1.5,inf),
)])
).play;
)
