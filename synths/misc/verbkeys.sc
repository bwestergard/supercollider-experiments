s.boot;

(

x = SynthDef(\pingverb, {arg note, gate;

var out,tone;

tone = SinOsc.ar(note.midicps);
tone = tone * Line.ar(1,0,0.4);
out = FreeVerb.ar(tone, 1, 1, 0.1) * 0.3;
out = BLowPass.ar(out, XLine.ar(20000,note.midicps,0.8), 0.2);

Out.ar(0, out);

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

newNode = Synth(\pingverb);
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
