s.boot;


(

var notes, on, off;


MIDIIn.connect;


notes = Array.newClear(128);  // array has one slot per possible MIDI note


on = Routine({

var event, newNode;

loop {

event = MIDIIn.waitNoteOn; // all note-on events

// play the note

newNode = Synth(\default, [\freq, event.b.midicps,

\amp, event.c * 0.00315 * 0.2]);  // 0.00315 approx. == 1 / 127 * 0.4

notes.put(event.b, newNode); // save it to free later

}

}).play;


off = Routine({

var event;

loop {

event = MIDIIn.waitNoteOff;

// look up the node currently playing on this slot, and release it

notes[event.b].set(\gate, 0);

}

}).play;


q = { on.stop; off.stop; };

)

