(
// chords. If an Array of pitches is returned by a Stream for pitch, then a chord
// will be played.
Pbind(
\instrument, \xfade,
\scale, [0,2,3,5,7,-4],
\octave, Pshuf([2,4,4,3],inf),
\dur, (1/4)*Pseq([
            Pseq([2],1), // 2 beats 
            Pseq([1],8), // 8 beats
            Pseq([Pseq([2],1),Pseq([3/2],4)])/2 // 2 beats
            ], inf), 
\degree, Pshuf( [7,1,2,3,0,1,2,3,0,Pshuf([4,5,34],2)], inf) + [0,14]
).play
)




(
SynthDef("xfade", {
arg dur,freq;
var out;
var note = freq.cpsmidi;
var gate = 1;
var tone = VarSaw.ar(note.midicps, width:0.5) + Pulse.ar(note.midicps*2, mul: XLine.ar(1,4,dur), width: XLine.ar(0.5,0.1,dur));
dur = dur *4;
out = DFM1.ar(tone, EnvGen.ar(Env.new([20, 6000, 400, 2], dur*[1/800, 1/20, 1/3],'exponential'),gate), EnvGen.ar(Env.new([0.01, 0.99, 0.8], [dur/12,dur/2],'exponential'),gate), noiselevel: 0, type: 0) * 0.1;
out = Compander.ar(out, out, thresh: 1/20, slopeAbove:1/20, clampTime: 1/40);
out = out * EnvGen.ar(Env.new([1/1000, 1, 0.8, 1/1000], dur*[0.001, 0.8, 0.1],'exponential'),gate, doneAction: 2);
Out.ar(0,out.dup * 3);
}).store;
)
