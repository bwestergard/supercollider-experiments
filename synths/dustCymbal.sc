(
{
	var f = 850544;
	var scratch = Dust.ar(f, 0.5);
	BPF.ar(scratch, f, 0.01).dup * XLine.ar(1,1e-10,20, doneAction: 2) * 200
}.play;
)
