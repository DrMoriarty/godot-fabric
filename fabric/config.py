def can_build(plat):
	return plat=="android"

def configure(env):
	if (env['platform'] == 'android'):
		env.android_add_maven_repository("url 'https://maven.fabric.io/public'")
		env.android_add_buildscript_maven_repository("url 'https://maven.fabric.io/public'")
		env.android_add_gradle_classpath("io.fabric.tools:gradle:1.+")
		env.android_add_gradle_plugin("io.fabric")
		env.android_add_dependency("compile('com.crashlytics.sdk.android:crashlytics:2.8.0@aar') { transitive = true; }")
		env.android_add_to_manifest("android/AndroidManifestChunk.xml")
		env.android_add_to_permissions("android/AndroidManifestPermissionsChunk.xml")
		env.android_add_java_dir("android/src/")
		env.disable_module()

