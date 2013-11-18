(
SynthDef("xfade", {
  arg freq,dur;
  var t = 4;
  var wfreq = (freq.cpsmidi).midicps;
  var tone = XFade2.ar( VarSaw.ar(wfreq, width:0.3), LFCub.ar(wfreq), XLine.ar(1/1000,1,t/2));
  tone = tone * XLine.ar(1, 1/10000, t, doneAction:2) * 0.3;
  tone = tone * Line.ar(1/1000,1, 0.01) * 0.3;
  tone = [DelayC.ar(tone, 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone, 1, LFNoise2.ar(1/2).range(0.012,0))] * 0.1;
	Out.ar(0, LeakDC.ar(tone.dup));
}).store;
)

(
// chords. If an Array of pitches is returned by a Stream for pitch, then a chord
// will be played.
var witch, kick;
witch = Pbind(
\instrument, \xfade,
\scale, [0,2,3,5,7,8],
\octave, Pseq([6,5],inf)-1,
\dur, Pseq([
            Pseq([3,Pconst(1, Prand([1/3,1/6], inf))],4)/2,
            Pseq([4],1)
            ], inf), 
\degree, Pseq([ 
Pshuf(#[0,2,4,0,2,4,8], 4)+[0,3],
Pshuf( [0,1,2,3,4,5,6,7]+[0,4] )
], inf)
);
kick = Pbind(
\instrument, Prand([\kik,\kraftySnr],inf),
\dur, Pn(Pconst(2, Prand([1/3,1/6,1,1/2], inf)),inf)
);

Ppar([witch, kick]).play;

)

SynthDef(\kraftySnr, { |amp = 1, freq = 2000, rq = 3, decay = 0.3, pan, out|

var sig = PinkNoise.ar(amp),

env = EnvGen.kr(Env.perc(0.01, decay), doneAction: 2);

sig = BPF.ar(sig, freq, rq, env);

Out.ar(out, Pan2.ar(sig, pan))

}).memStore;
