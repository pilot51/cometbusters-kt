buildscript {
	repositories {
		mavenCentral()
		jcenter()
	}
	dependencies {
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
	}
}

allprojects {
	repositories {
		jcenter()
		mavenCentral()
	}
}
