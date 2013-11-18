s.boot;

(

x = SynthDef(\filtpulse, {arg note, gate;

var in,out,tone,note;
note = note + LFNoise2.ar(0.3, 1/16);
in = SinOsc.ar(note.midicps, phase: LocalIn.ar());
LocalOut.ar(DelayC.ar(in, 1, LFNoise2.ar(0.1,0.005) + 0.01) * 0.8);
LocalOut.ar(DelayC.ar(in, 1, LFNoise2.ar(0.1,0.05) + 0.1) * 0.7);
LocalOut.ar(SinOsc.ar((note+7).midicps) * Line.ar(1,0,3));
LocalOut.ar(SinOsc.ar((note+12).midicps) * Line.ar(1,0,7));
out = in + LocalIn.ar();
out.dup * 0.05;

}).send(s);

)


(

var notes, on, off;
MIDIIn.connect;
notes = Array.newClear(128);  // array has one slot per possible MIDI note

on = Routine({

var event, newNode;

loop {

event = MIDIIn.waitNoteOn;

newNode = Synth(\filtpulse);
newNode.set(\note, event.b);
newNode.set(\gate, 1);
notes.put(event.b, newNode);

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
