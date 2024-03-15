----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 04/22/2023 06:36:33 PM
-- Design Name: 
-- Module Name: MEM - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;
use ieee.numeric_std.all;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity MEM is
Port(clk:in STD_LOGIC;
  en:in STD_LOGIC;
  ALUResIn:in STD_LOGIC_VECTOR(15 downto 0);
  RD2:in STD_LOGIC_VECTOR(15 downto 0);
  MemWrite:in STD_LOGIC;
  MemData:out STD_LOGIC_VECTOR(15 downto 0);
  ALUResOut:out STD_LOGIC_VECTOR(15 downto 0));
end MEM;

architecture Behavioral of MEM is
type ram_type is array (15 downto 0) of std_logic_vector(15 downto 0);
signal RAM : ram_type:=(
--SYNGLE-CYCLE
--B"0000000000000011",
--B"0000000000000011",
--B"0000000000000100",
--B"0000000000000110",
--PIPELINE
others=>x"0000");
begin
ALUResOut<=ALUResIn;
process(clk)
begin
if (clk'event and clk = '1') then
if (en = '1') then
if(MemWrite='1') then
RAM(conv_integer(ALUResIn)) <= RD2;
end if;
end if;
end if;
end process;
process(en)
begin 
if(en='1') then
MemData<=RAM(conv_integer(ALUResIn));
end if;
end process;
end Behavioral;
