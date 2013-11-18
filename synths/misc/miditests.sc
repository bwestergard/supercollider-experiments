Server.local.options.memSize = 50*8192;

SynthDef("simple", { arg start_note=50, end_note=50, gate=1;

var out,note;

note = Line.kr(start_note,end_note,1) + LFNoise2.kr(16, 1/16);

out = XFade2.ar(LFTri.ar(note.midicps),SinOsc.ar(note.midicps),Line.kr(1,0,1)) * 0.1;

out = out * EnvGen.kr(Env.adsr, gate, 1, doneAction: 2);

Out.ar(0, out);
Out.ar(1, out);

}).send(s);

//

(

var notes, on, off, last_note;
MIDIIn.connect;
notes = Array.newClear(128);  // array has one slot per possible MIDI note

on = Routine({

var event, newNode;

loop {

event = MIDIIn.waitNoteOn;

newNode = Synth("simple");
newNode.set(\start_note, last_note);
newNode.set(\end_note, event.b);
notes.put(event.b, newNode);
last_note = event.b;

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
