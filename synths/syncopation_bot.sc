b = Buffer.read(s, "/home/bjorn/audio_work/samples/imogen.wav");

(

var a = Pbind(
\bufnum, b,
\instrument, \imogen,
\scale, Scale.major,
\octave, 7,
\amp, Pstep(Pseq([Pgeom(1,0.90,16)],inf), 1/16),

\degree, Pseq([Pstep(Pseq([0, 2, 4, -1], 1), 4)], inf)+30, 

\dur, PdurStutter(

Pseq([2,2,4],inf),

Pwrand([1/4,1/2,1.5,1,1/3], [10, 15, 1, 1, 1/2].normalizeSum, inf)

)

);

a.play;

)

SynthDef(\imogen, {| out = 0, bufnum = 0, freq = 400, dur = 1, gate = 1 |
var rate, tone;
rate = ((freq/40000) * Rand(1,2).round(1));
tone = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum), rate: rate, startPos: Rand(0,BufFrames.kr(bufnum)), doneAction:2).dup;
dur = dur * 4;
tone = tone * EnvGen.kr(Env.adsr,gate, doneAction:2);
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.0012,-0.0012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.0012,-0.0012))];
tone = MoogLadder.ar(tone, XLine.ar(MouseX.kr(20,20000),MouseY.kr(20,20000),dur), MouseButton.kr(0.2,0.9)) * 4;
Out.ar(out, tone)

}).store();
