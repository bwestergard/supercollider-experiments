s.boot;

(

SynthDef(\filtpulse, {arg note, gate;

var out, carrier;

note = note + LFNoise2.ar(0.4, 1/12);

carrier = Pulse.ar(note.midicps, width: LFNoise2.ar(0.3, 0.1) + 0.5) * 0.08;
carrier = carrier + (Pulse.ar(note.midicps * 3/2, width: LFNoise2.ar(0.3, 0.1) + 0.5) * 0.05 * EnvGen.kr(Env.asr(3, 1, 0.4), gate, 1));
carrier = carrier + (Pulse.ar(note.midicps * 2, width: LFNoise2.ar(0.3, 0.1) + 0.5) * LFTri.ar(3) * 0.05 * EnvGen.kr(Env.asr(5, 1, 0.4), gate, 1));
out = RLPF.ar(carrier, (Line.ar(note+24, note+4, 3)).midicps, Line.ar(0.5,0.9,3));

out = out * EnvGen.kr(Env.asr(0.01, 1, 0.6), gate, 1, doneAction: 2) * AmpComp.kr(note.midicps, 20.midicps) * 0.5;

Out.ar(0, out.dup);

}).send(s);

)

(
var noteDict = Dictionary.new;
NoteOnResponder.removeAll;
NoteOffResponder.removeAll;

NoteOnResponder
({|src, chan, note, vel|


noteDict.put(note, Synth(\filtpulse, [\note, note, \gate, 1]));
});

NoteOffResponder
({|src, chan, note, vel|


noteDict.at(note).set(\gate, 0);
noteDict.removeAt(\note);
});
)
