(
var snare, kick, kraft, k, s, w;
var drums, clicks;
var a,b;

c = [4,[[1,[1,1,[1,[1,1,1]],1]],[1,[[3,[1,1]],1]],[1,[1,1,[1,[3,1]]]],1]].convertRhythm;
a = [4,[1,1,[1,[1,1,[1,[3,1]]]],1]].convertRhythm;
b = [4,[1,[1,[1,1,1,5]],4]].convertRhythm;

TempoClock.default.tempo = (70/60);

snare = Buffer.read(s, "/home/bjorn/audio_work/samples/budos_snare.wav");
kick = Buffer.read(s, "/home/bjorn/audio_work/samples/electro_kick.wav");
kraft = Buffer.read(s, "/home/bjorn/audio_work/samples/kraft_prog.wav");
k = kick.bufnum;
s = snare.bufnum;
w = kraft.bufnum;


drums = Pbind(
       \instrument, \drum_machine,
       \bufnum, Pstep([k,[s,w],k,[k,w]],1,inf),
       \dur, Pseq([a,a,c,b].flatten,inf),
       \amp, 0.15,
       \rate, Pswitch1([1,Prand([1,2,3,4],inf)/Prand([1,2,3],inf)],Pstep([0,1],4,inf),inf)
       );

clicks = Pbind(
       \instrument, \click,
       \amp, Pstep(Pseq([1,0.3],inf),1/6) * Pstep([0,0,1,1,0,0,0,1],1/4,inf) * Pstep([1,1,1,0],4,inf),
       \dur, 1/8
       );

Ppar([drums,clicks]).play;
)

SynthDef(\drum_machine, {| out = 0, bufnum = 0, rate = 1, amp = 1, dur = 1|
var playback;
playback = PlayBuf.ar(2, bufnum, BufRateScale.kr(bufnum), rate: rate, doneAction:2) * 0.8;
playback = playback * XLine.ar(1, 1/1000, dur*4, doneAction:2);
playback = RLPF.ar(playback, XLine.ar(20,20000,dur/64), 0.8);
Out.ar(out, playback * amp)
}).store();

SynthDef(\click, {| out = 0, amp = 0, dur = 1 |
Out.ar(out, XLine.ar(1,1/1000,dur*2, doneAction:2) * BPF.ar(PinkNoise.ar,XLine.ar(20000,LinLin.kr(amp,0,1,200,1000),dur),XLine.ar(0.99,0.01,dur)).dup * 0.8 * amp);
}).store();
