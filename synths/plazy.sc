~dur = [4,[[1,1!3],[1,1!6],[1,1!2],1]].convertRhythm;
~leg = [3];
~pitches = [0, 2, 4, [6,8]] ++ [-2, 5, 4, [6,8]];

 Pbind(
 		\instrument, \saw,
    \dur, Pn(Plazy{ Pseq(~dur) }),
    \degree, Pstep(Plazy{ Pseq(~pitches) }, 1/8, inf),
    \amp, 0.025,
    \legato, Pn(Plazy{ Pseq(~leg) })
 ).play
 
SynthDef(\saw, {|freq = 440, sustain = 1, gate = 1|
var tone,env,wenv;
env = Env.adsr(0.02, 0.2, 0.25, 1, 1, -4);
wenv = Env.adsr(0.6, 0.2, 0.5, 1, 1, -4);
tone = VarSaw.ar(freq * [1,2.001,1/3], width: EnvGen.kr(wenv, gate, doneAction: 2)).sum / 5;
//tone = MoogLadder.ar(tone, MouseX.kr(20,2000), 0.1);
tone = tone * EnvGen.kr(env, gate, doneAction: 2);
Out.ar(0,tone.dup);
}).store;

Env.adsr(0.02, 0.2, 0.25, 1, 1, -4).plot

~echo = Synth(\dubecho, [\length, TempoClock.default.tempo*(3/8), \fb, 0.7, \gate, 1, \sep, 0.0012], addAction: \addToTail);
~echo.free;
