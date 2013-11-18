(
SynthDef(\scansynth1, { arg out=0, amp=0.1, gate=1, pan=0, freq=200;
    var in, ou;
    var spring = (0.1).dup(40);
    var damp = (0.7).dup(40);
    var springs;
    var sel;
    in = LPF.ar(LocalIn.ar(1), XLine.ar(1,10,0.3)) * 0.97 + (LPF.ar(Trig.ar(gate, 0.01),200) * WhiteNoise.ar.range(0.98,1));
    ou = in;
    springs = spring.collect { arg spr, i;
        ou = Spring.ar(ou, spr, damp[i]);
    };
    LocalOut.ar(ou);
    sel = VarSaw.ar(freq * [0.999,1,0.499,2,1.01] * 0.5, width: 0).range(0, spring.size-1);
    ou = Splay.ar(SelectX.ar(sel, springs), 1);
    ou = ou * EnvGen.ar(\adsr.kr(Env.adsr(0.3,0.1,0.8,0.1)),gate,doneAction:2);
    ou = Pan2.ar(ou, pan, amp);
    Out.ar(out, ou);
}).add;
)

Synth(\scansynth1);

TempoClock.default.tempo = 0.3

(
Pdef(\plop, Pbind(
    \instrument, \scansynth1,
    \scale, Scale.minor,
    \degree, Pseq([0,2,4],inf) + [0,2,4,-12,7],
    \strum, 1/6,
    \dur, 3,
    \amp, 2,
    \legato, 1.4
)).play;
)
