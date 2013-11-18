
b = Buffer.read(s, "/home/bjorn/audio_work/samples/nina_ghost.wav");
SynthDef(\loop_chop, {| out = 0, bufnum = 0, gate = 0, min = 1, pos = 2|
var a = (Schmidt.ar(SoundIn.ar([0]), 0, 0) - 0.5) * 2;
var c = (Schmidt.ar(SoundIn.ar([1]), 0, 0) - 0.5) * 2;
var direction = Latch.ar(a,c);
var freq = ZeroCrossing.ar(SoundIn.ar([0])) * direction;
var rate = freq / 1000;
var level = Clip.kr(gate+min,0,1);
var sig =   BufRd.ar(1, b,
    Phasor.ar(gate, BufRateScale.kr(b) * rate, 0, BufFrames.kr(b), BufFrames.kr(b)*pos)
  ).dup * level;
Out.ar(out,
sig
)
}).store();

~rekkid = Synth(\loop_chop, [\out, 0, \bufnum, b]);
~dubecho = Synth(\dubecho, [\length, 3*(b.duration/32), \fb, 0.6], addAction: \addToTail);
~dubecho.free
MIDIIn.connect;

MIDIIn.noteOn = {|port,chan,note,vel|
[port,chan,64-note,vel].postln;
//Synth(\klangpad, [\note, note;, \velocity, vel]);
~rekkid.set(\pos, (64-note)/16);
~rekkid.set(\gate, 1);
};

MIDIIn.noteOff = {|port,chan,note,vel|
~rekkid.set(\gate, 0);
};

MIDIIn.noteOn = nil;

c = CCResponder({ |src,chan,num,value|
[src,chan,num,value].postln;
(num == 74).if({~rekkid.set(\min, value/128)});
(num == 10).if({~dubecho.set(\acceptance, value/128)});
});


~rekkid.set(\pos, 3/16);
~rekkid.set(\t_trig, 1);
