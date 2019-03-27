#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D sprite;
uniform vec3 color;

void main() // TODO : Use postprocessing
{
    fragColor = vec4(1.0 - texture(sprite, fragTexCoords).rgb, texture(sprite, fragTexCoords).a);
}
