	// Definir aquí los atributos de la clase.

	public void run() {

		// Colocar aquí el código que se ejecuta solo una vez al principio
		// Ejemplo: setColors(Color.RED, Color.YELLOW, Color.CYAN);

		while (true) {
			// Colocar aquí lo que el Robot hará todo el tiempo
		}

	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
		//Colocar aquí el código que se ejecutará cuando se detecta otro robot 
	}
	
	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		// Colocar aquí el código que se ejecutará cuando el robot fue alcanzado
		// por una bala
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		//Colocar aquí el código que se ejecutará cuando el robot choca con un muro
	}

	@Override
	public void onHitRobot(HitRobotEvent event) {
		//Colocar aquí el código que se ejecutará cuando el robot choca con otro robot
	}


