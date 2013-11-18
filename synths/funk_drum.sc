(
var snare, kick, alt_kick, snare_part, clicks;

TempoClock.default.tempo = 1;

snare = Buffer.read(s, "/home/bjorn/audio_work/samples/budos_snare.wav");
kick = Buffer.read(s, "/home/bjorn/audio_work/samples/kick.wav");
alt_kick = Buffer.read(s, "/home/bjorn/audio_work/samples/kraft_prog.wav");

snare_part = Pbind(
       \instrument, \drum_machine,
       \div, 16,
       \bjorklund, Pbjorklund(Prand([7,9,13,5],inf),Pkey(\div)),
       \bufnum, Pstep(Pclutch(Pseq([kick.bufnum,alt_kick.bufnum,snare.bufnum,alt_kick.bufnum],inf), Pbjorklund(Pn(Prand([3,6,7,2],8),inf),8)),1/8),
       \dur, 2/Pkey(\div),
       \amp, Pkey(\bjorklund) * 0.15 * Pstep(Pseq([1,0.6],inf),1/8,inf) + Pseg(Pseq([0,0.01],inf),Prand([1,4,0.1,3,8],inf), \sine),
       \sep, Pstep([0,0,0.012],2,inf).trace
       );

clicks = Pbind(
       \instrument, \click,
       \amp, Pstep(Pseq([1,0.3],inf),1/6) * 0.3,
       \dur, Pstep(Pseq([1/4,1/4,1/4,Prand([1/6,1/8])],inf), 1)
       );

Ptpar([0,snare_part,8,clicks]).play;

)


SynthDef(\drum_machine, {| out = 0, bufnum = 0, rate = 1, amp = 1, dur = 1,sep = 0|
var playback;
playback = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum), rate: rate).dup * 0.8 * XLine.ar(1,1,dur, doneAction:2);
playback = [DelayC.ar(playback, 1, LFNoise2.ar(1/2).range(0,sep)), DelayC.ar(playback, 1, LFNoise2.ar(1/2).range(sep,0))];
Out.ar(out, playback * amp)
}).store();

/*
SynthDef(\drum_machine, {| out = 0, bufnum = 0, rate = 1, amp = 1, dur = 1,sep = 0|
var playback;
playback = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum), rate: rate * Rand(1,2).round(1), doneAction:2).dup * 0.8 * XLine.ar(2,1,8*dur, doneAction:2);
playback = [DelayC.ar(playback, 1, LFNoise2.ar(1/2).range(0,sep)), DelayC.ar(playback, 1, LFNoise2.ar(1/2).range(sep,0))];
Out.ar(out, playback * amp)
}).store();
*/

SynthDef(\click, {| out = 0, amp = 0, dur = 1 |
Out.ar(out, XLine.ar(1,1/1000,dur*2, doneAction:2) * BPF.ar(PinkNoise.ar,XLine.ar(20000,LinLin.kr(amp,0,1,200,1000),dur),XLine.ar(0.99,0.01,dur)).dup * 0.8 * amp);
}).store();

(
SynthDef("sinepluck", {
arg freq = 440, amp = 1, dur = 1, detune = 1/4, out = 0;
var mod,tone;
dur = dur;
amp = amp * 0.8;
mod = VarSaw.ar(freq*2, mul: XLine.ar(0.2,0.8,dur/2), width: XLine.ar(1,1/1000,dur*64));
tone = SinOsc.ar(freq, mod).dup * 0.1 * XLine.ar(1,1/1000,dur*4, doneAction:2) * XLine.ar(1/1000,1,0.001) * amp;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * XLine.ar(1/10000,1,0.005);
Out.ar(out,tone);
}).store;
)

(
SynthDef("ital_bass", {
arg freq = 440, amp = 1, dur = 1, detune = 1/4, out = 0, wobble = 1;
var mod,tone,foo;
tone = VarSaw.ar(Lag.kr(freq), width:LFNoise2.ar(3).range(0.3,0.1));
tone = MoogLadder.ar(tone, SinOsc.ar(wobble).range(20,4000), 0.1) * 4;
tone = tone * XLine.ar(8,1/1000,dur, doneAction:2).tanh;
Out.ar(out,tone.dup * amp);
}).store;
)
