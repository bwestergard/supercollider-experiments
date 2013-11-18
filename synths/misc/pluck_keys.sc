s.boot;

(

x = SynthDef(\plink, {arg note, gate;

var freq = note.midicps;
var sep_max = 0.012;
var sep_l = Rand(0, sep_max);
var sep_r = Rand(0, sep_max);
var wobble = 2;
var time = LinLin.ar(note,80,40,1,4);
var tone;
var harsh_scale = LinLin.ar(note, 100, 20, 4, 1);
var mod = LocalIn.ar + SinOsc.ar(freq*3, mul: XLine.ar(harsh_scale,0.3,time/2)) + SinOsc.ar(freq*7, mul: XLine.ar(0.3 * harsh_scale,0,time*2));
tone = SinOsc.ar(freq, phase: mod);
LocalOut.ar(tone * XLine.ar(1,1/10000,time));
tone = [DelayN.ar(tone, sep_max, sep_l), DelayN.ar(tone, sep_max, sep_r)];
Out.ar(0,tone * 0.2 * XLine.ar(1,1/100000,time, doneAction: 2));
}).store;

)

Synth(\plink, [\note, 60]);
Synth(\plink, [\note, 60+3]);
Synth(\plink, [\note, 60+7]);
Synth(\plink, [\note, 60+8]);

(

var notes, on, off;
MIDIIn.connect;
notes = Array.newClear(128);  // array has one slot per possible MIDI note

on = Routine({

var event, newNodeA, newNodeB;

loop {

event = MIDIIn.waitNoteOn;

newNodeA = Synth(\plink, [\note, event.b]);
newNodeB = Synth(\plink, [\note, event.b+12]);
notes.put(event.b, newNodeA);
notes.put(event.b+12, newNodeB);

}

}).play;


off = Routine({

var event;

loop {

event = MIDIIn.waitNoteOff;

notes[event.b].set(\gate, 0);

}

}).play;

q = { on.stop; off.stop; };

)

q.value;
