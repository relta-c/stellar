#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D sprite; // TODO : Check this
uniform vec3 color;

void main()
{
    fragColor = vec4(color.rgb, 1.0f) * texture(sprite, fragTexCoords);
}
