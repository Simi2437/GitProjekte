package Physik;

public class Geometrie {
	
//	EllypsenRechnung für die halbachsen:
//		x²/a² + y²/b² = 1
//		umgestellt auf a =
//		x²/a² = 1 - y²/b²
//		a² = x²/(1-y²/b²)
//		umgestellt auf b = 
//		y²/b² = 1 - x²/a²
//		b² = y²/(1-x²/a²)
//		in 1:
//			a² = x²/(1-y²/(y² / (1-x²/a²)))
//	BESTER ANSATZ:
//		x1²*a² + y1²*b² = a²b²
//		x2²*a² + y2²*b² = a²b²    ::2-1  Problem --> x = x-P1.y y = y - P1.y
}
