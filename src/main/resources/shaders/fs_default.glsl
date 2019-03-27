#version 330 core

in vec2 fragTexCoords;

out vec4 fragColor;

uniform sampler2D sprite; // TODO : Check this
uniform vec3 color;
uniform bool haveTexture;

void main()
{
    if (!haveTexture) {
        fragColor = vec4(color.rgb, 1.0f);
    } else {
        fragColor = vec4(color.rgb, 1.0f) * texture(sprite, fragTexCoords);
    }
}
