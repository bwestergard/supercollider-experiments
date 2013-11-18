SynthDef(\zap, {arg note, gate;

var out, carrier, voices;

voices = 10;

note = note + LFNoise2.ar(0.4, 1/12);

out = VarSaw.ar(note.midicps, 0.1, Line.ar(0.8,0.3,2) + LFNoise2.ar(0.3,0.1));

out = MoogVCF.ar(out, Line.ar(note.midicps * 8, note.midicps * 1.5, 2), Line.ar(0.1, 0.85, 0.1)).dup;

out = out * EnvGen.kr(Env.asr(0.01, 1, 1), gate, 1, doneAction: 2) * AmpComp.kr(note.midicps, 20.midicps) * 0.5;

Out.ar(0, out.dup);

}).send(s);


Synth(\zap, [\note, 30, \gate, 1]);
Synth(\zap, [\note, 40, \gate, 1]);

(
var noteDict = Dictionary.new;
NoteOnResponder.removeAll;
NoteOffResponder.removeAll;

NoteOnResponder
({|src, chan, note, vel|


noteDict.put(note, Synth(\zap, [\note, note, \gate, 1]));
});

NoteOffResponder
({|src, chan, note, vel|


noteDict.at(note).set(\gate, 0);
noteDict.removeAt(\note);
});
)
