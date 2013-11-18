s.boot;

(

x = SynthDef(\filtpulse, {arg note, gate;

var out, carrier, sweep_time;

sweep_time = LinLin.kr(note, 36, 80, 0.5, 0.1);

note = note + LFNoise2.ar(0.5, 1/8);

carrier = VarSaw.ar(note.midicps, width: LinLin.kr(note, 24, 65, 0.5, 0.1));

PrintVal.kr(note);

out = BLowPass.ar(

carrier,

XLine.ar(10000, note.midicps * 3/2,sweep_time), // cutoff freq.

XLine.ar(0.11, 0.5, 6), // rq

0.5) * 0.1;

out = out * EnvGen.kr(Env.asr(0.1, 1, 0.4), gate, 1, doneAction: 2) * AmpComp.kr(note.midicps, 20.midicps);

Out.ar(0, out.dup);

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
